package com.cactus.marvelcomics.data.network.model

import android.os.Parcelable
import com.cactus.marvelcomics.data.db.ComicEntity
import com.cactus.marvelcomics.data.db.SerieEntity
import com.cactus.marvelcomics.features.characters.domain.MarvelCharacter
import kotlinx.android.parcel.Parcelize

open class BaseWrapper {
    val code: Int? = null
    val status: String? = null
}

open class BaseContainer {
    val limit: Int? = null
    val count: Int? = null
    val total: Int? = null
    val offset: Int? = null
}

//  POJO Character
data class CharacterDataWrapper(
    var data: CharacterDataContainer? = null
) : BaseWrapper()

data class CharacterDataContainer(
    var results: List<Character>? = null
) : BaseContainer()

data class Character(
    var id: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var thumbnail: MarvelImage? = null,
    var series: BaseList? = null,
    var comics: BaseList? = null
)

data class BaseList(
    val items: List<BaseSummary>? = null
)

@Parcelize
data class BaseSummary(
    val resourceURI: String? = null,
    val name: String? = null
) : Parcelable


//  POJO Comic
data class ComicDataWrapper(
    var data: ComicDataContainer? = null
) : BaseWrapper()

data class ComicDataContainer(
    var results: List<Comic>? = null
) : BaseContainer()

@Parcelize
data class Comic(
    var id: Int? = null,
    var title: String? = null,
    var thumbnail: MarvelImage? = null
) : Parcelable


//  POJO Serie
data class SerieDataWrapper(
    var data: SerieDataContainer? = null
) : BaseWrapper()

data class SerieDataContainer(
    var results: List<Serie>? = null
) : BaseContainer()

@Parcelize
data class Serie(
    var id: Int? = null,
    var title: String? = null,
    var thumbnail: MarvelImage? = null
) : Parcelable


@Parcelize
data class MarvelImage(
    val path: String? = null,
    val extension: String? = null,
    val uri: String? = "$path.$extension"
) : Parcelable


fun Character.toMarvelCharacter(): MarvelCharacter {
    return with(this) {
        MarvelCharacter(
            id = this.id!!.toLong(),
            name = this.name!!,
            description = this.description!!,
            thumbnail = this.thumbnail!!.uri!!,
            comics = this.comics!!.items!!,
            series = this.comics!!.items!!
        )
    }
}


fun Comic.toComicEntity(characterId: Long) = ComicEntity(0, characterId, this.title!!, this.thumbnail?.uri!!)

fun Serie.toSerieEntity(characterId: Long) = SerieEntity(0, characterId, this.title!!, this.thumbnail?.uri!!)




