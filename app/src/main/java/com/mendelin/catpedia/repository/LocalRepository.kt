package com.mendelin.catpedia.repository

import androidx.annotation.Keep
import com.mendelin.catpedia.CatpediaApplication
import com.mendelin.catpedia.utils.ResourceUtils
import com.mendelin.catpedia.retrofit.RetrofitService
import com.squareup.moshi.JsonAdapter

@Keep
class LocalRepository<T>(private val id: Int, private val classOfT: Class<T>) : Repository<T> {

    override fun readData(): T? {
        val file = CatpediaApplication.getInstance().resources.openRawResource(id)
        val strData = ResourceUtils.readTextFile(file)

        val adapter: JsonAdapter<T> = RetrofitService.moshi.adapter(classOfT)
        return adapter.fromJson(strData)
    }
}