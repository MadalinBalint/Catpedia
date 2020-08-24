package com.mendelin.catpedia.data_access_layer.networking

import com.mendelin.catpedia.data_access_layer.networking.models.responses.BreedImageResponse
import com.mendelin.catpedia.data_access_layer.networking.models.responses.BreedInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CatpediaRestApi {
    @GET("v1/breeds")
    fun getListOfCatBreeds(): Call<ResponseObject<List<BreedInfoResponse>>>

    @GET("images/search?breed_ids={breedId}")
    fun getBreedInfo(@Query("breedId") breedId: String): Call<ResponseObject<BreedImageResponse>>
}