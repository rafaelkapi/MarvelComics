package com.cactus.marvelcomics.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cactus.marvelcomics.data.db.CharacterEntity

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save (character: CharacterEntity)

    @Query("SELECT * FROM character WHERE id = :id ")
    suspend fun getCharacter(id: Long): CharacterEntity

    @Query("SELECT * FROM character")
    suspend fun getAllCharacters(): List<CharacterEntity>

}