package com.example.kotlincrud.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlincrud.R
import com.example.kotlincrud.databinding.ActivityUpdateNoteBinding
import com.example.kotlincrud.model.NotesModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class UpdateNoteActivity : AppCompatActivity() {
    private lateinit var binding:ActivityUpdateNoteBinding
    private lateinit var databaseRef: DatabaseReference
    private lateinit var storageRef: StorageReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().reference.child("notes")
        storageRef = FirebaseStorage.getInstance().reference.child("note_images")
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var note : NotesModel? = intent.getParcelableExtra("note")

        // Display data in the views
        binding.titleInput.setText(note?.title)
        binding.descriptionInput.setText(note?.description)
        Picasso.get().load(note?.imageUrl).into(binding.imageView)

        binding.save.setOnClickListener(){
            val updatedTitle = binding.titleInput.text.toString()
            val updatedDescription = binding.descriptionInput.text.toString()
        }



    }
}