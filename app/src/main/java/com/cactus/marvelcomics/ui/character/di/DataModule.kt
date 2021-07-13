package com.cactus.marvelcomics.ui.character.di

import android.annotation.SuppressLint
import com.cactus.marvelcomics.data.network.MarvelService
import com.cactus.marvelcomics.ui.character.data.CharacterRepository
import com.cactus.marvelcomics.ui.character.data.CharacterRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
abstract class DataModule {

    @Binds
    abstract fun provideLocalDataSource(repository: CharacterRepositoryImp): CharacterRepository

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideService(retrofit: Retrofit): MarvelService =
            retrofit.create(MarvelService::class.java)
    }

}