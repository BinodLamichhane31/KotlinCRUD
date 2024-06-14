package com.example.kotlincrud.repository

import android.media.MediaRouter.SimpleCallback
import com.example.kotlincrud.model.NotesModel

interface NotesRepository {
    fun addNote(notesModel: NotesModel,imageData:ByteIterator,callback:(Boolean,String?)->Unit)
    fun getNote(callback:(List<NotesModel>)->Unit)
    fun updateNote(notesModel: NotesModel,imageData:ByteIterator,callback:(Boolean,String?)->Unit)
    fun addNote(noteId:String,callback:(Boolean,String?)->Unit)


}