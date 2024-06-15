package com.example.kotlincrud.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincrud.databinding.SampleNoteItemBinding
import com.example.kotlincrud.model.NotesModel
import com.example.kotlincrud.view.UpdateNoteActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class NotesAdapter(var context: Context, var notes :
ArrayList<NotesModel>): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    class NotesViewHolder(private val binding: SampleNoteItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var title:TextView = binding.titleTextView
        val description:TextView = binding.descriptionTextView
        val image:ImageView = binding.imageView
        val button:ImageButton = binding.editButton
        val progressBar:ProgressBar = binding.progressBar


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = SampleNoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = notes[position]

        holder.title.text = notes[position].title
        holder.description.text = notes[position].description
        var imageUrl = notes[position].imageUrl
        Picasso.get().load(imageUrl).into(holder.image,object: Callback {
            override fun onSuccess() {
                holder.progressBar.visibility = View.INVISIBLE
            }

            override fun onError(e: Exception?) {
            }

        })
        holder.button.setOnClickListener {
            var intent = Intent(context, UpdateNoteActivity::class.java)
            intent.putExtra("note",note)
            context.startActivity(intent)
        }

    }
    fun updateNotes(newNotes: ArrayList<NotesModel>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}