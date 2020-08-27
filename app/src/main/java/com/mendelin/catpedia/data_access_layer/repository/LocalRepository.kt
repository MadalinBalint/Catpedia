package com.mendelin.catpedia.data_access_layer.repository

import androidx.annotation.Keep
import com.mendelin.catpedia.business_layer.CatpediaApplication
import com.mendelin.catpedia.common.ResourceUtils
import com.mendelin.catpedia.data_access_layer.networking.RetrofitService
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@Keep
class LocalRepository<T>(private val id: Int, private val classOfT: Class<T>) : Repository<T> {

    override fun readData(): T? {
        val file = CatpediaApplication.getInstance().resources.openRawResource(id)
        val strData = ResourceUtils.readTextFile(file)

        val adapter: JsonAdapter<T> = RetrofitService.moshi.adapter(classOfT)
        return adapter.fromJson(strData)
    }
}