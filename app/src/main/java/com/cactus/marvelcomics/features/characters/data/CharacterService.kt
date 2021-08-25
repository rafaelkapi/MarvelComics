package com.cactus.marvelcomics.features.characters.data

import com.cactus.marvelcomics.data.network.model.CharacterDataWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {

    @GET("v1/public/characters")
     suspend fun getCharacters(@Query("limit") limit: Int,
                      @Query("offset") offset: Int): Response<CharacterDataWrapper>



    @GET("v1/public/characters")
    fun getCharactersBeginLetter(@Query("limit") limit: Int,
                                 @Query("offset") offset: Int,
                                 @Query("nameStartsWith") letter: String): Response<CharacterDataWrapper>

    @GET("/v1/public/characters/{characterId}")
    fun getCharacter(@Path("characterId") characterId: Int?): Response<CharacterDataWrapper>
}
