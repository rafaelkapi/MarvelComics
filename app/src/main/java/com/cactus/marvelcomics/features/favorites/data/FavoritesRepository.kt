package com.cactus.marvelcomics.features.favorites.data

import com.cactus.marvelcomics.data.db.AppDataBase
import com.cactus.marvelcomics.data.db.toComic
import com.cactus.marvelcomics.data.db.toSerie
import com.cactus.marvelcomics.data.network.BaseDataSource
import com.cactus.marvelcomics.features.characters.domain.MarvelCharacter
import javax.inject.Inject

interface FavoritesRepository {

    suspend fun getCharactersFavorites() : List<MarvelCharacter>
    suspend fun deleteCharacter(character: MarvelCharacter)
}

class FavoritesRepositoryImp @Inject constructor(
    private val db: AppDataBase
) : BaseDataSource(), FavoritesRepository {


    override suspend fun getCharactersFavorites(): List<MarvelCharacter> {
        val listMarvelCharacter = mutableListOf<MarvelCharacter>()

        db.CharacterDao().getAllCharacters().forEach { characterEntity ->
            listMarvelCharacter.add(MarvelCharacter(
                id = characterEntity.id,
                name = characterEntity.name,
                description = characterEntity.description,
                thumbnail = characterEntity.thumbnail,
                favorite = true,
                comicsDb = db.CharacterDao().getAllComics(characterEntity.id).map { it.toComic() },
                seriesDb = db.CharacterDao().getAllSeries(characterEntity.id).map { it.toSerie() } ,
                comics = listOf(),
                series = listOf()
            ))
        }
        return listMarvelCharacter
    }

    override suspend fun deleteCharacter(character: MarvelCharacter) {
        db.CharacterDao().deleteCharacter(character.id)
    }

}
