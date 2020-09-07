package com.mendelin.catpedia.rest_service

import com.mendelin.catpedia.retrofit.RetrofitService

object CatpediaApi {
    val service: CatpediaApiService by lazy {
        RetrofitService().createService(CatpediaApiService::class.java)
    }
}