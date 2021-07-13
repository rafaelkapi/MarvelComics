package com.cactus.marvelcomics.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "character")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String,
    val thumbnail: String
)