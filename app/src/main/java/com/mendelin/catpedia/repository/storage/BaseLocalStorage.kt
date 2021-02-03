package com.mendelin.catpedia.repository.storage

import android.content.Context
import androidx.annotation.Keep
import com.google.gson.Gson
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

@Keep
class BaseLocalStorage<T>(private val gson: Gson, private val id: Int, private val classOfT: Class<T>) {
    private fun readTextFile(inputStream: InputStream): String {
        val outputStream = ByteArrayOutputStream()

        val buf = ByteArray(4096)
        var len = 0
        try {
            while ({ len = inputStream.read(buf); len }() != -1) {
                outputStream.write(buf, 0, len)
            }
            outputStream.close()
            inputStream.close()
        } catch (e: IOException) {
        }

        return outputStream.toString()
    }

    fun readData(context: Context): T? {
        val file = context.resources.openRawResource(id)
        val strData = readTextFile(file)

        return gson.fromJson(strData, classOfT)
    }
}