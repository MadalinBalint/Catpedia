package com.mendelin.catpedia.data_access_layer.networking

import com.mendelin.catpedia.data_access_layer.networking.models.BreedImageResponse
import com.mendelin.catpedia.data_access_layer.networking.models.BreedInfoResponse
import com.mendelin.catpedia.presentation_layer.activities.welcome_screen.bussiness_logic.models.LoginResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CatpediaApiService {
    @GET("v1/breeds")
    suspend fun getListOfCatBreeds(): List<BreedInfoResponse>

    @GET("v1/images/search")
    suspend fun getBreedImage(@Query("breed_id") breedId: String): List<BreedImageResponse>

    /* To use when backend endepoint is available */
    @GET("v1/login.php")
    suspend fun loginUser(@Query("username") username: String,
                          @Query("password") password: String): LoginResponse
}