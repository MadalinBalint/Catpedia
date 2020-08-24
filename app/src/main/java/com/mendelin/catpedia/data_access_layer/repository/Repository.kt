package com.mendelin.catpedia.data_access_layer.repository

import androidx.annotation.Keep

@Keep
interface Repository<T> {
    fun readData(): T? = null
}