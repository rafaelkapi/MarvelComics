package com.cactus.marvelcomics.features.characters.data

import com.cactus.marvelcomics.data.OperationResult
import com.cactus.marvelcomics.data.db.AppDataBase
import com.cactus.marvelcomics.data.db.toComic
import com.cactus.marvelcomics.data.network.BaseDataSource
import com.cactus.marvelcomics.features.characters.domain.MarvelCharacter
import com.cactus.marvelcomics.features.characters.domain.converterListComicEntity
import com.cactus.marvelcomics.features.characters.domain.converterListSerieEntity
import com.cactus.marvelcomics.features.characters.domain.toCharacterEntity
import javax.inject.Inject

interface CharacterRepository {
    suspend fun getCharacterList(limit: Int, offset: Int): OperationResult
    suspend fun saveCharacter(character: MarvelCharacter)
    suspend fun deleteCharacter(character: MarvelCharacter)
    suspend fun getCharactersFavorites(): List<MarvelCharacter>
}

class CharacterRepositoryImp @Inject constructor(
    private val service: CharacterService,
    private val db: AppDataBase
) : BaseDataSource(), CharacterRepository {

    override suspend fun getCharacterList(
        limit: Int,
        offset: Int
    ): OperationResult =
        getResult { service.getCharacters(limit, offset) }

    override suspend fun saveCharacter(character: MarvelCharacter) {
        db.CharacterDao().save(character.toCharacterEntity())
        db.CharacterDao().saveComics(character.converterListComicEntity() ?: listOf())
        db.CharacterDao().saveSeries( character.converterListSerieEntity() ?: listOf())
    }

    override suspend fun deleteCharacter(character: MarvelCharacter) {
        db.CharacterDao().deleteCharacter(character.id)
    }

    override suspend fun getCharactersFavorites(): List<MarvelCharacter> {
        val listMarvelCharacter = mutableListOf<MarvelCharacter>()

        db.CharacterDao().getAllCharacters().forEach {
            listMarvelCharacter.add(
                MarvelCharacter(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    thumbnail = it.thumbnail,
                    comicsDb = db.CharacterDao().getAllComics(it.id).map { it.toComic() },
                    comics = listOf(),
                    series = listOf()
                )
            )
        }
        return listMarvelCharacter
    }

}
