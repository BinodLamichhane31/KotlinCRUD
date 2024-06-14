package com.example.kotlincrud.repository.impl

import com.example.kotlincrud.model.UserModel
import com.example.kotlincrud.repository.UserRepository
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class NotesRepositoryImpl:UserRepository {
    var auth:FirebaseAuth = FirebaseAuth.getInstance()
    var database:FirebaseDatabase = FirebaseDatabase.getInstance()
    var storage:FirebaseStorage = FirebaseStorage.getInstance()

    val databaseRef:DatabaseReference = database.reference.child("notes")
    val storageRef:StorageReference = storage.reference.child("note_images")

    override fun registerUser(
        user: UserModel,
        password: String,
        callback: (Boolean, String?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun loginUser(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getUser(callback: (UserModel?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun logOut() {
        TODO("Not yet implemented")
    }
}