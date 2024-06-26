package com.example.kotlincrud.repository.impl

import android.util.Log
import com.example.kotlincrud.model.NotesModel
import com.example.kotlincrud.model.UserModel
import com.example.kotlincrud.repository.NotesRepository
import com.example.kotlincrud.repository.UserRepository
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
            callback(false, exception.message)
        }

    }


    override fun getNote(callback: (List<NotesModel>) -> Unit) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            callback(emptyList())
            return
        }
        val uid = currentUser.uid
        databaseRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val notesList = mutableListOf<NotesModel>()
                for (noteSnapshot in dataSnapshot.children) {
                    val note = noteSnapshot.getValue(NotesModel::class.java)
                    note?.let { notesList.add(it) }
                }
                callback(notesList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                callback(emptyList())
            }
        })
    }

    override fun updateNote(
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
        val noteId = notesModel.id
        val noteRef = databaseRef.child(uid).child(noteId)
        val imageRef = storageRef.child(uid).child(noteId)

        if (imageData.isNotEmpty()) {
            // Update both note data and image
            val uploadTask = imageRef.putBytes(imageData)

            uploadTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        val updatedNote = notesModel.copy(imageUrl = uri.toString())
                        noteRef.setValue(updatedNote)
                            .addOnSuccessListener {
                                callback(true, null)
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
                callback(false, exception.message)
            }
        } else {
            // Only update note data, not the image
            noteRef.setValue(notesModel)
                .addOnSuccessListener {
                    callback(true, null)
                }
                .addOnFailureListener { exception ->
                    callback(false, exception.message)
                }
        }
    }


    override fun deleteNote(noteId: String, callback: (Boolean, String?) -> Unit) {
        TODO("Not yet implemented")
    }


}