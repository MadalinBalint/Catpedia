package com.mendelin.catpedia.data_access_layer.networking

import com.mendelin.catpedia.data_access_layer.networking.models.responses.BreedImageResponse
import com.mendelin.catpedia.data_access_layer.networking.models.responses.BreedInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CatpediaApiService {
    @GET("v1/breeds")
    suspend fun getListOfCatBreeds(): List<BreedInfoResponse>

    @GET("v1/images/search")
    suspend fun getBreedImage(@Query("breed_id") breedId: String): MutableList<BreedImageResponse>
}