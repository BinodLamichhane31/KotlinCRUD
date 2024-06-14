package com.example.kotlincrud.view

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlincrud.databinding.ActivityAddNoteBinding
import com.example.kotlincrud.model.NotesModel
import com.example.kotlincrud.repository.impl.NotesRepositoryImpl
import com.example.kotlincrud.utils.ImageUtils
import com.example.kotlincrud.utils.LoadingUtils
import com.example.kotlincrud.viewmodel.NotesViewModel

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    lateinit var notesViewModel: NotesViewModel
    private var selectedImage: ByteArray? = null
    lateinit var loadingUtils: LoadingUtils


    private lateinit var imagePickerLauncher: ActivityResultLauncher<Unit>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var repo = NotesRepositoryImpl()
        notesViewModel= NotesViewModel(repo)

        initializeImagePickerLauncher()
        loadingUtils = LoadingUtils(this)


        binding.save.setOnClickListener {
            val title = binding.titleInput.text.toString().trim()
            val description = binding.descriptionInput.text.toString().trim()

            if (title.isNotEmpty() && description.isNotEmpty() && selectedImage != null) {
                loadingUtils.showLoading()
                val note = NotesModel(title = title, description = description)
                selectedImage?.let { imageData ->
                    notesViewModel.addNote(note, imageData) { success, message ->
                        if (success) {
                            Toast.makeText(this, "Note added successfully", Toast.LENGTH_SHORT).show()
                            finish()
                            loadingUtils.dismiss()
                        } else {
                            Toast.makeText(this, "Failed to add note: $message", Toast.LENGTH_SHORT).show()
                            loadingUtils.dismiss()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageView.setOnClickListener {
            launchImagePicker()
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

}
