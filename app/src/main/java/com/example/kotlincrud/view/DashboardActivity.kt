package com.example.kotlincrud.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlincrud.R
import com.example.kotlincrud.adapter.NotesAdapter
import com.example.kotlincrud.databinding.ActivityDashboardBinding
import com.example.kotlincrud.databinding.ActivityLoginBinding

class DashboardActivity : AppCompatActivity() {
    lateinit var binding:ActivityDashboardBinding
    lateinit var notesAdapter: NotesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        notesAdapter= NotesAdapter(this@DashboardActivity,ArrayList())


        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@DashboardActivity)
            adapter = notesAdapter
        }

        binding.addNodes.setOnClickListener(){
            startActivity(Intent(this@DashboardActivity,AddNoteActivity::class.java))
        }


    }
}