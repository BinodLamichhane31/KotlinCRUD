package com.example.kotlincrud.repository.impl

import android.util.Log
import com.example.kotlincrud.model.NotesModel
import com.example.kotlincrud.model.UserModel
import com.example.kotlincrud.repository.NotesRepository
import com.example.kotlincrud.repository.UserRepository
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class NotesRepositoryImpl : NotesRepository {
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var storage: FirebaseStorage = FirebaseStorage.getInstance()

    val databaseRef: DatabaseReference = database.reference.child("notes")
    val storageRef: StorageReference = storage.reference.child("note_images")
    override fun addNote(
        notesModel: NotesModel,
        imageData: ByteArray,
        callback: (Boolean, String?) -> Unit
    ) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            callback(false, "User not authenticated")
            return
        }

        val uid = currentUser.uid
        val noteId = databaseRef.child(uid).push().key ?: ""
        val imageRef = storageRef.child(uid).child(noteId)
        val uploadTask = imageRef.putBytes(imageData)

        uploadTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    val newNote = NotesModel(
                        id = noteId,
                        title = notesModel.title,
                        description = notesModel.description,
                        imageUrl = uri.toString(),
                    )
                    databaseRef.child(uid).child(noteId).setValue(newNote)
                        .addOnSuccessListener {
                            callback(true, "Note added successfully.")
                        }
                        .addOnFailureListener { exception ->
                            callback(false, exception.message)
                        }
                }.addOnFailureListener { exception ->
                    callback(false, exception.message)
                }
            } else {
                callback(false, task.exception?.message)
            }
        }.addOnFailureListener { exception ->
            Log.e("NotesRepositoryImpl", "Image upload failed: ${exception.message}")
            callback(false, exception.message)
        }

    }


    override fun getNote(callback: (List<NotesModel>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun updateNote(
        notesModel: NotesModel,
        imageData: ByteIterator,
        callback: (Boolean, String?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun deleteNote(noteId: String, callback: (Boolean, String?) -> Unit) {
        TODO("Not yet implemented")
    }


}