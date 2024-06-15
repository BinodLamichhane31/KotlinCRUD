package com.example.kotlincrud.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlincrud.R
import com.example.kotlincrud.adapter.NotesAdapter
import com.example.kotlincrud.databinding.ActivityDashboardBinding
import com.example.kotlincrud.databinding.ActivityLoginBinding
import com.example.kotlincrud.repository.NotesRepository
import com.example.kotlincrud.repository.impl.NotesRepositoryImpl
import com.example.kotlincrud.viewmodel.NotesViewModel

class DashboardActivity : AppCompatActivity() {
    lateinit var binding:ActivityDashboardBinding
    lateinit var notesAdapter: NotesAdapter
    private lateinit var notesViewModel: NotesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        notesAdapter= NotesAdapter(this@DashboardActivity,ArrayList())
        val repo = NotesRepositoryImpl()
        notesViewModel= NotesViewModel(repo)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@DashboardActivity)
            adapter = notesAdapter
        }

        binding.addNodes.setOnClickListener(){
            startActivity(Intent(this@DashboardActivity,AddNoteActivity::class.java))
        }

        fetchNotes()


    }
    fun fetchNotes(){
        notesViewModel.getNote { notesList ->
            val arrayList = ArrayList(notesList)
            notesAdapter.updateNotes(arrayList)
        }
    }
}