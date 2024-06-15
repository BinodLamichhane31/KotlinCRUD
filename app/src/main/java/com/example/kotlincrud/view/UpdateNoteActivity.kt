package com.example.kotlincrud.view
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlincrud.databinding.ActivityUpdateNoteBinding
import com.example.kotlincrud.model.NotesModel
import com.example.kotlincrud.repository.impl.NotesRepositoryImpl
import com.example.kotlincrud.utils.ImageUtils
import com.example.kotlincrud.viewmodel.NotesViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class UpdateNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var databaseRef: DatabaseReference
    private lateinit var storageRef: StorageReference
    private var note: NotesModel? = null
    private var selectedImage: ByteArray? = null
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Unit>
    private lateinit var notesViewModel: NotesViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        databaseRef = FirebaseDatabase.getInstance().reference.child("notes")
        storageRef = FirebaseStorage.getInstance().reference.child("note_images")
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var repo = NotesRepositoryImpl()
        notesViewModel= NotesViewModel(repo)

        // Retrieve note object from intent
        note = intent.getParcelableExtra("note")

        // Display existing data in views
        binding.titleInput.setText(note?.title)
        binding.descriptionInput.setText(note?.description)
        Picasso.get().load(note?.imageUrl).into(binding.imageView)

        // Handle image click to pick new image
        binding.imageView.setOnClickListener {
            launchImagePicker()
        }
        initializeImagePickerLauncher()

        // Handle update button click
        binding.save.setOnClickListener {
            val updatedTitle = binding.titleInput.text.toString()
            val updatedDescription = binding.descriptionInput.text.toString()

            if (updatedTitle.isNotEmpty() && updatedDescription.isNotEmpty()) {
                // Update note object
                note?.title = updatedTitle
                note?.description = updatedDescription

                // Upload new image if imageUri is not null
                if (selectedImage != null) {
                    uploadImageAndUpdateNote()

                } else {
                    updateNoteWithoutImage()
                }
            } else {
                Toast.makeText(this@UpdateNoteActivity, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initializeImagePickerLauncher() {
        imagePickerLauncher = registerForActivityResult(PickImageContract()) { result ->
            result?.let { uri ->
                selectedImage = ImageUtils.getImageBytesFromUri(this,uri)
                binding.imageView.setImageURI(uri)
            }
        }
    }

    private fun launchImagePicker() {
        imagePickerLauncher.launch(Unit)
    }

    private fun updateNoteWithoutImage() {
        notesViewModel.updateNote(note!!, byteArrayOf()) { success, message ->
            if (success) {
                Toast.makeText(this@UpdateNoteActivity, "Note updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this@UpdateNoteActivity, "Failed to update note: $message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadImageAndUpdateNote() {
        // After successful upload, update note with new image URL
        // Then call updateNote on ViewModel
        val imageRef = storageRef.child("${note?.id}.jpg")
        imageRef.putBytes(selectedImage!!)
            .addOnSuccessListener { taskSnapshot ->
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    note?.imageUrl = uri.toString()

                    notesViewModel.updateNote(note!!, selectedImage!!) { success, message ->
                        if (success) {
                            Toast.makeText(
                                this@UpdateNoteActivity,
                                "Note updated successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this@UpdateNoteActivity,
                                "Failed to update note: $message",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this@UpdateNoteActivity,
                    "Failed to upload image: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
