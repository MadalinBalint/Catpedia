package com.mendelin.catpedia.networking

import com.mendelin.catpedia.BuildConfig
import com.mendelin.catpedia.models.BreedImageResponse
import com.mendelin.catpedia.models.BreedInfoResponse
import com.mendelin.catpedia.models.LoginResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CatpediaApiService {
    @GET(BuildConfig.ENDPOINT_BREEDS)
    fun getListOfCatBreeds(): Observable<List<BreedInfoResponse>>

    @GET(BuildConfig.ENDPOINT_SEARCH)
    fun getBreedImage(@Query(BuildConfig.QUERY_BREED_ID) breedId: String,
                      @Query(BuildConfig.QUERY_SIZE) size: String = "thumb"): Single<List<BreedImageResponse>>

    /* To use when backend endpoint is available */
    @GET(BuildConfig.ENDPOINT_LOGIN)
    fun loginUser(@Query(BuildConfig.QUERY_USERNAME) username: String,
                  @Query(BuildConfig.QUERY_PASSWORD) password: String): Single<LoginResponse>
}