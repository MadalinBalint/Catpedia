package com.mendelin.catpedia.repository

import android.content.Context
import androidx.annotation.Keep
import com.mendelin.catpedia.retrofit.RetrofitService
import com.mendelin.catpedia.utils.ResourceUtils
import com.squareup.moshi.JsonAdapter

@Keep
class LocalRepository<T>(private val id: Int, private val classOfT: Class<T>) {
    fun readData(context: Context): T? {
        val file = context.resources.openRawResource(id)
        val strData = ResourceUtils.readTextFile(file)

        val adapter: JsonAdapter<T> = RetrofitService.moshi.adapter(classOfT)
        return adapter.fromJson(strData)
    }
}