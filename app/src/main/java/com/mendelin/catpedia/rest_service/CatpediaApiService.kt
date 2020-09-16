package com.mendelin.catpedia.rest_service

import com.mendelin.catpedia.BuildConfig
import com.mendelin.catpedia.breed_info.models.BreedImageResponse
import com.mendelin.catpedia.breeds_list.models.BreedInfoResponse
import com.mendelin.catpedia.welcome_screen.bussiness_logic.models.LoginResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CatpediaApiService {
    @GET(BuildConfig.ENDPOINT_BREEDS)
    suspend fun getListOfCatBreeds(): List<BreedInfoResponse>

    @GET(BuildConfig.ENDPOINT_SEARCH)
    suspend fun getBreedImage(@Query(BuildConfig.QUERY_BREED_ID) breedId: String,
                              @Query(BuildConfig.QUERY_SIZE) size: String = "thumb"): List<BreedImageResponse>

    /* To use when backend endpoint is available */
    @GET(BuildConfig.ENDPOINT_LOGIN)
    suspend fun loginUser(@Query(BuildConfig.QUERY_USERNAME) username: String,
                          @Query(BuildConfig.QUERY_PASSWORD) password: String): LoginResponse
}