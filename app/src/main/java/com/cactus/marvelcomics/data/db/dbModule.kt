package com.cactus.marvelcomics.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class dbModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@BindsInstance context: Context): RoomDatabase {
        return Room.databaseBuilder(context, Appdatabase::class.java, "dbName")
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .build()
    }
}