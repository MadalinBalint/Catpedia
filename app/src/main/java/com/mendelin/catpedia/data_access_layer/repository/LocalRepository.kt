package com.mendelin.catpedia.data_access_layer.repository

import androidx.annotation.Keep
import com.google.gson.GsonBuilder
import com.mendelin.catpedia.business_layer.CatpediaApplication
import com.mendelin.catpedia.common.ResourceUtils


@Keep
class LocalRepository<T>(private val id: Int, private val classOfT: Class<T>) : Repository<T> {
    private val DATE_TIME_ZONE_XML_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"

    override fun readData(): T? {
        val file = CatpediaApplication.getInstance().resources.openRawResource(id)
        val strData = ResourceUtils.readTextFile(file)
        val gson = GsonBuilder().setDateFormat(DATE_TIME_ZONE_XML_FORMAT).create()
        return gson.fromJson(strData, classOfT)
    }
}