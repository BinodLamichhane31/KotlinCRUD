package com.example.kotlincrud.repository

import com.example.kotlincrud.model.UserModel

interface UserRepository {
    fun registerUser(user: UserModel, password: String, callback: (Boolean, String?) -> Unit)
    fun loginUser(email:String,password: String,callback: (Boolean, String?) -> Unit)
    fun getUser(callback: (UserModel?) -> Unit)
    fun logOut();
}