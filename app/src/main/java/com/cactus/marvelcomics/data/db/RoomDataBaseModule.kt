package com.cactus.marvelcomics.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class RoomDataBaseModule() {

    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, "dbName")
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .build()
    }
}