package com.cactus.marvelcomics.data.network

import com.cactus.marvelcomics.data.network.model.DataWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelService {

    @GET("v1/public/characters")
    fun getCharacters(@Query("limit") limit: Int,
                      @Query("offset") offset: Int): Response<DataWrapper>

    @GET("v1/public/characters")
    fun getCharactersBeginLetter(@Query("limit") limit: Int,
                                 @Query("offset") offset: Int,
                                 @Query("nameStartsWith") letter: String): Response<DataWrapper>

    @GET("/v1/public/characters/{characterId}")
    fun getCharacter(@Path("characterId") characterId: Int?): Response<DataWrapper>
}
