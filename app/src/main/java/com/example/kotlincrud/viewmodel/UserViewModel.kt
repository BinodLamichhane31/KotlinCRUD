package com.example.kotlincrud.viewmodel

import androidx.lifecycle.ViewModel
import com.example.kotlincrud.model.UserModel
import com.example.kotlincrud.repository.UserRepository

class UserViewModel(val userRepository: UserRepository):ViewModel() {
    fun registerUser(user: UserModel, password: String, callback: (Boolean, String?) -> Unit){
        userRepository.registerUser(user,password,callback)
    }
    fun loginUser(email:String,password: String,callback: (Boolean, String?) -> Unit){
        userRepository.loginUser(email,password,callback)

    }
    fun getUser(callback: (UserModel?) -> Unit){
        userRepository.getUser(callback)

    }
    fun logOut(){
        userRepository.logOut()

    }
}