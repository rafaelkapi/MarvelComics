package com.cactus.marvelcomics.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cactus.marvelcomics.data.db.dao.CharacterDao

@Database(entities = [ CharacterEntity::class, ComicEntity::class, SerieEntity::class ], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun CharacterDao(): CharacterDao


}