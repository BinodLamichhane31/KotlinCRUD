package com.example.kotlincrud.viewmodel

import androidx.lifecycle.ViewModel
import com.example.kotlincrud.model.NotesModel
import com.example.kotlincrud.repository.NotesRepository

class NotesViewModel(val notesRepository: NotesRepository):ViewModel() {

    fun addNote(notesModel: NotesModel, imageData:ByteArray, callback:(Boolean, String?)->Unit){
        notesRepository.addNote(notesModel,imageData,callback)

    }
    fun getNote(callback:(List<NotesModel>)->Unit){
        notesRepository.getNote(callback)

    }
    fun updateNote(notesModel: NotesModel, imageData:ByteIterator, callback:(Boolean, String?)->Unit){

    }
    fun addNote(noteId:String,callback:(Boolean,String?)->Unit){

    }
}