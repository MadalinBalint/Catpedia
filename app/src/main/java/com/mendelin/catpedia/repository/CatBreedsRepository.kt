package com.mendelin.catpedia.repository

import com.mendelin.catpedia.models.BreedImageResponse
import com.mendelin.catpedia.models.BreedInfoResponse
import com.mendelin.catpedia.networking.CatpediaApiService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CatBreedsRepository @Inject constructor(private val service: CatpediaApiService) {

    fun getCatBreeds(): Single<List<BreedInfoResponse>> {
        return service.getListOfCatBreeds()
            .subscribeOn(Schedulers.io())
            .flatMapIterable { list -> list }
            .flatMapSingle { breed ->
                getBreedImage(breed.id)
                    .map { imageList -> breed.image = imageList?.firstOrNull() }
                    .flatMap { Single.just(breed) }
            }
            .toList()
    }

    private fun getBreedImage(breedId: String): Single<List<BreedImageResponse>> = service.getBreedImage(breedId)
}