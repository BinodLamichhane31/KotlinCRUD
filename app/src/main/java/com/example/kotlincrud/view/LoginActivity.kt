package com.example.kotlincrud.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.kotlincrud.databinding.ActivityLoginBinding
import com.example.kotlincrud.utils.LoadingUtils
import com.example.kotlincrud.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var loadingUtils: LoadingUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingUtils = LoadingUtils(this)

        binding.signUp.setOnClickListener(){
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
        }

        binding.loginButton.setOnClickListener(){
            val email = binding.emailLogin.text.toString()
            val password = binding.passwordLogin.text.toString()

            if(email.isNotEmpty()&&password.isNotEmpty()){
                loadingUtils.showLoading()
                userViewModel.loginUser(email,password){success,message->
                    loadingUtils.showLoading()
                    if(success){
                        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity,DashboardActivity::class.java))
                        finish()
                    }
                    else{
                        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
                 }
                }

            }
            else{
                Toast.makeText(applicationContext,"Enter email/password",Toast.LENGTH_SHORT).show()
            }
        }

    }
}