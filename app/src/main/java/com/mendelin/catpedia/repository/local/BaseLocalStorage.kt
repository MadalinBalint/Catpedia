package com.mendelin.catpedia.repository.local

import android.content.Context
import androidx.annotation.Keep
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

@Keep
class BaseLocalStorage<T>(private val moshi: Moshi, private val id: Int, private val classOfT: Class<T>) {
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

        val adapter: JsonAdapter<T> = moshi.adapter(classOfT)
        return adapter.fromJson(strData)
    }
}