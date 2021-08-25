package com.cactus.marvelcomics.features.details.data

import com.cactus.marvelcomics.data.network.model.ComicDataWrapper
import com.cactus.marvelcomics.data.network.model.SerieDataWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ComisSeriesService {

    @GET("/v1/public/comics/{comicId}")
    suspend fun getComic(@Path("comicId") characterId: Int?): Response<ComicDataWrapper>

    @GET("/v1/public/series/{seriesId}")
    suspend fun getSerie(@Path("seriesId") characterId: Int?): Response<SerieDataWrapper>


}