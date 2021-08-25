package com.cactus.marvelcomics.data.db

import androidx.room.*
import com.cactus.marvelcomics.data.network.model.BaseSummary
import com.cactus.marvelcomics.data.network.model.Comic
import com.cactus.marvelcomics.data.network.model.MarvelImage
import com.cactus.marvelcomics.data.network.model.Serie


@Entity(tableName = "marvelcharacters")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = false) val id: Long = 0,
    val name: String ,
    val description: String ,
    val thumbnail: String ,
)

@Entity(tableName = "comic")
data class ComicEntity(
    @PrimaryKey(autoGenerate = true) val comicId: Long = 0,
    val characterId: Long,
    val title: String,
    val uri: String
)

@Entity(tableName = "serie")
data class SerieEntity(
    @PrimaryKey(autoGenerate = true) val serieId: Long = 0,
    val characterId: Long,
    val title: String,
    val uri: String
)


fun ComicEntity.toComic() : Comic {
    return Comic(null, this.title, MarvelImage(null,null, this.uri))
}

fun SerieEntity.toSerie() : Serie {
    return Serie(null, this.title, MarvelImage(null,null, this.uri))
}





