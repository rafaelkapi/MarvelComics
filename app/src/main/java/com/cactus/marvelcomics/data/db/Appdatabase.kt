package com.cactus.marvelcomics.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cactus.marvelcomics.data.db.dao.CharacterDao

@Database(entities = [CharacterEntity::class], version = 1)
abstract class Appdatabase : RoomDatabase() {

    abstract fun CharacterDao(): CharacterDao


}