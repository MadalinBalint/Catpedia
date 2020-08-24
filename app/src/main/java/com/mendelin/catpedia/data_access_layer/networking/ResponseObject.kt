package com.mendelin.catpedia.data_access_layer.networking

open class ResponseObject<T> {
    val status: Boolean? = null
    val message: String? = null

    var data: T? = null

    override fun toString(): String {
        return "ResponseObject(status=$status, message=$message, data=$data)"
    }
}