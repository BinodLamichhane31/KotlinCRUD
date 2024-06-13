package com.example.kotlincrud.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlincrud.databinding.ActivitySignUpBinding
import com.example.kotlincrud.model.UserModel
import com.example.kotlincrud.viewmodel.UserViewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logIn.setOnClickListener(){
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
        }

        binding.signupButton.setOnClickListener(){
            val name = binding.nameSignup.text.toString()
            val email = binding.emailSignup.text.toString()
            val phone = binding.phoneSignup.text.toString()
            val password = binding.passwordSignup.text.toString()
            val confirmPassword = binding.confirmPasswordSignup.text.toString()

            if(name.isNotEmpty()&&email.isNotEmpty()&&phone.isNotEmpty()&&password.isNotEmpty()&&confirmPassword.isNotEmpty()){
                if(password==confirmPassword){
                    val user = UserModel(name=name, email = email, phone = phone)
                    userViewModel.registerUser(user,password){success,message->
                        if(success){
                            startActivity(Intent(this@SignUpActivity,LoginActivity::class.java))
                            Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(applicationContext,"Registration failed",Toast.LENGTH_SHORT).show()

                        }
                    }

                }
                else{
                    Toast.makeText(applicationContext,"Password and confirm password should be similar.",Toast.LENGTH_SHORT).show()


                }

            }
            else{
                Toast.makeText(applicationContext,"Please fill all the fields.",Toast.LENGTH_SHORT).show()
            }
        }

    }
}