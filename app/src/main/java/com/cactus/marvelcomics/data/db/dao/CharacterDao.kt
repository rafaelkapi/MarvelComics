package com.cactus.marvelcomics.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cactus.marvelcomics.data.db.CharacterEntity
import com.cactus.marvelcomics.data.db.ComicEntity
import com.cactus.marvelcomics.data.db.SerieEntity

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save (marvelcharacters: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveComics (comicList: List<ComicEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSeries (serieList: List<SerieEntity>)

    @Query("DELETE FROM marvelcharacters WHERE id = :id ")
    suspend fun deleteCharacter(id: Long)

    @Query("SELECT * FROM marvelcharacters WHERE id = :id ")
    suspend fun getCharacter(id: Long): CharacterEntity

    @Query("SELECT * FROM comic WHERE characterId = :id ")
    suspend fun getAllComics(id: Long): List<ComicEntity>

    @Query("SELECT * FROM serie WHERE characterId = :id ")
    suspend fun getAllSeries(id: Long): List<SerieEntity>

    @Query("SELECT * FROM marvelcharacters")
    suspend fun getAllCharacters(): List<CharacterEntity>

}