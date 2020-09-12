package com.mendelin.catpedia.repository

import androidx.annotation.Keep

@Keep
interface Repository<T> {
    fun readData(): T?
}