package com.example.kotlincrud.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.InputStream

object ImageUtils {
     fun getImageBytesFromUri(context: Context, uri: Uri): ByteArray? {
        val contentResolver: ContentResolver = context.contentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        inputStream?.let {
            val buffer = ByteArrayOutputStream()
            val bufferSize = 1024
            val byteArray = ByteArray(bufferSize)
            var bytesRead: Int
            while (inputStream.read(byteArray, 0, bufferSize).also { bytesRead = it } != -1) {
                buffer.write(byteArray, 0, bytesRead)
            }
            return buffer.toByteArray()
        }
        return null
    }

}