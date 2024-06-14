package com.example.kotlincrud.repository.impl

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
        }
        val uid = currentUser?.uid ?: ""
        val noteId = databaseRef.child(uid).push().key ?: ""
        val uploadTask = storageRef.putBytes(imageData)

        uploadTask.addOnCompleteListener() {
            if (it.isSuccessful) {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    val newNote = NotesModel(
                        id = noteId,
                        title = notesModel.title,
                        description = notesModel.description,
                        imageUrl = uri.toString()
                    )
                }
                databaseRef.child(uid).child(noteId).setValue(notesModel)
                    .addOnSuccessListener { callback(true, "Note added successfully.") }
                    .addOnFailureListener { callback(false, it.message) }
            } else {
                callback(false,it.exception?.message)
            }
        }

    }


    override fun addNote(noteId: String, callback: (Boolean, String?) -> Unit) {
        TODO("Not yet implemented")
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


}