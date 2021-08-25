package com.cactus.marvelcomics.features.characters.domain

import android.os.Parcelable
import com.cactus.marvelcomics.data.db.*
import com.cactus.marvelcomics.data.network.model.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarvelCharacter(
    val id: Long,
    val name: String,
    val description: String,
    val thumbnail: String,
    var favorite: Boolean = false,
    var comicsDb: List<Comic>? = null,
    var seriesDb: List<Serie>? = null,
    val comics: List<BaseSummary>,
    val series: List<BaseSummary>
) : Parcelable

fun MarvelCharacter.toCharacterEntity(): CharacterEntity {
    return with(this) {
        CharacterEntity(
            id = this.id,
            name = this.name,
            description = this.description,
            thumbnail = this.thumbnail,
        )
    }
}

fun MarvelCharacter.converterListComicEntity() = this.comicsDb?.map { it.toComicEntity(this.id) }

fun MarvelCharacter.converterListSerieEntity() = this.seriesDb?.map { it.toSerieEntity(this.id) }




