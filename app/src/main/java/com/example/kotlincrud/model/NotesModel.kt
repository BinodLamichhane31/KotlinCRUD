package com.example.kotlincrud.model

import android.os.Parcel
import android.os.Parcelable

data class NotesModel(
    val id:String="",
    var title:String="",
    var description: String="",
    var imageUrl:String=""


):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NotesModel> {
        override fun createFromParcel(parcel: Parcel): NotesModel {
            return NotesModel(parcel)
        }

        override fun newArray(size: Int): Array<NotesModel?> {
            return arrayOfNulls(size)
        }
    }

}