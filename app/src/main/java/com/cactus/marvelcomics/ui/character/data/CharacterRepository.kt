package com.cactus.marvelcomics.ui.character.data

import com.cactus.marvelcomics.data.OperationResult
import com.cactus.marvelcomics.data.network.BaseDataSource
import com.cactus.marvelcomics.data.network.MarvelService
import javax.inject.Inject

interface CharacterRepository {
    suspend fun getCharacterList(limit: Int = 20, offset: Int = 0): OperationResult
//    fun getCharacterNameStartsWith(limit: Int = 20, offset: Int = 0, startsWith: String = ""): Single<MarvelCharacterDataContainer>
//    fun getCharacter(id: Int): Single<MarvelCharacterDataContainer>
}

class CharacterRepositoryImp @Inject constructor(private val service: MarvelService) :
    BaseDataSource(),
    CharacterRepository {

    override suspend fun getCharacterList(
        limit: Int,
        offset: Int
    ): OperationResult =
        getResult { service.getCharacters(limit, offset) }


//        resultLiveData<OperationResult>(
//            databaseQuery = null,
//            networkCall = { getResult { service.getCharacters(limit, offset) } },
//            saveCallResult = null
//        ).distinctUntilChanged()

//    override fun getCharacterNameStartsWith(
//        limit: Int,
//        offset: Int,
//        startsWith: String
//    ): Single<MarvelCharacterDataContainer> =
//        service.getCharactersBeginLetter(limit, offset, startsWith)
//            .map {
//                it.data
//            }
//
//    override fun getCharacter(
//        id: Int
//    ): Single<MarvelCharacterDataContainer> =
//        service.getCharacter(id)
//            .map {
//                it.data
//            }
}
