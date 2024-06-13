package com.example.kotlincrud.repository.impl

import com.example.kotlincrud.model.UserModel
import com.example.kotlincrud.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserRepositoryImpl:UserRepository {
    private val auth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val ref: DatabaseReference = database.getReference("users")
    override fun registerUser(
        user: UserModel,
        password: String,
        callback: (Boolean, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(user.email,password)
            .addOnCompleteListener(){
                if(it.isSuccessful){
                    val uid = auth.currentUser?.uid?:return@addOnCompleteListener
                    user.id=uid
                    ref.child(uid).setValue(user)
                        .addOnCompleteListener(){
                            if(it.isSuccessful){
                                callback(true,"User registration successful")
                            }
                            else{
                                callback(false,"User registration failed")
                            }
                        }


                }
                else{
                    callback(false,"User authentication failed")
                }
            }
    }

    override fun loginUser(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(){
                if(it.isSuccessful){
                    callback(true,"Login Successful")
                }
                else{
                    callback(true,"Login Failed")
                }
            }
    }

    override fun getUser(callback: (UserModel?) -> Unit) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userRef = ref.child(currentUser.uid)
            userRef.get().addOnSuccessListener { dataSnapshot ->
                val user = dataSnapshot.getValue(UserModel::class.java)
                callback(user)
            }.addOnFailureListener {
                callback(null)
            }
        } else {
            callback(null)
        }
    }

    override fun logOut() {
        auth.signOut()
    }
}