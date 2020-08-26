package com.mendelin.catpedia.data_access_layer.repository

import androidx.annotation.Keep
import com.mendelin.catpedia.business_layer.CatpediaApplication
import com.mendelin.catpedia.common.ResourceUtils
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@Keep
class LocalRepository<T>(private val id: Int, private val classOfT: Class<T>) : Repository<T> {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    override fun readData(): T? {
        val file = CatpediaApplication.getInstance().resources.openRawResource(id)
        val strData = ResourceUtils.readTextFile(file)

        val adapter: JsonAdapter<T> = moshi.adapter(classOfT)
        return adapter.fromJson(strData)
    }
}