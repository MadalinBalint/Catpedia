package com.mendelin.catpedia.repositories

import android.content.Context
import androidx.annotation.Keep
import com.mendelin.catpedia.utils.ResourceUtils
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

@Keep
class LocalRepository<T>(private val moshi: Moshi, private val id: Int, private val classOfT: Class<T>) {
    fun readData(context: Context): T? {
        val file = context.resources.openRawResource(id)
        val strData = ResourceUtils.readTextFile(file)

        val adapter: JsonAdapter<T> = moshi.adapter(classOfT)
        return adapter.fromJson(strData)
    }
}