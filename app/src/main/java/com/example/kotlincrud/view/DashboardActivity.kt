package com.example.kotlincrud.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlincrud.R
import com.example.kotlincrud.databinding.ActivityDashboardBinding
import com.example.kotlincrud.databinding.ActivityLoginBinding

class DashboardActivity : AppCompatActivity() {
    lateinit var binding:ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addNodes.setOnClickListener(){
            startActivity(Intent(this@DashboardActivity,AddNoteActivity::class.java))
        }


    }
}