package com.example.kotlincrud.utils

import android.app.Activity
import android.app.AlertDialog
import com.example.kotlincrud.R

class LoadingUtils(val activity:Activity) {

    lateinit var dialog: AlertDialog

    fun showLoading(){
        var dialogView = activity.layoutInflater.inflate(R.layout.loading_layout,null)

        var builder = AlertDialog.Builder(activity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()

    }
    fun dismiss(){
        dialog.dismiss()
    }
}