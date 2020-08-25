package com.mendelin.catpedia.data_access_layer.networking

object CatpediaApi {
    val service: CatpediaApiService by lazy {
        RetrofitService().createService(CatpediaApiService::class.java)
    }
}