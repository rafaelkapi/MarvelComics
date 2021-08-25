package com.cactus.marvelcomics.features.characters.di

import androidx.lifecycle.ViewModel
import com.cactus.marvelcomics.common.ViewModelKey
import com.cactus.marvelcomics.di.FragmentScope
import com.cactus.marvelcomics.features.characters.data.CharacterRepository
import com.cactus.marvelcomics.features.characters.data.CharacterRepositoryImp
import com.cactus.marvelcomics.features.characters.data.CharacterService
import com.cactus.marvelcomics.features.characters.presentation.CharactersFragment
import com.cactus.marvelcomics.features.characters.presentation.CharactersViewModel
import com.cactus.marvelcomics.features.comics_series.di.ComicsSeriesModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module
abstract class CharacterModuleBuilder {
    @FragmentScope
    @ContributesAndroidInjector(modules = [CharacterDataModule::class, CharacterModule::class])
    abstract fun bindsCharactersFragment(): CharactersFragment
}

@Module
abstract class CharacterModule {

    @Binds
    @IntoMap
    @ViewModelKey(CharactersViewModel::class)
    abstract fun bindCharactersViewModel(viewModel: CharactersViewModel): ViewModel

}

@Module
abstract class CharacterDataModule {

    @Binds
    abstract fun provideLocalDataSource(repository: CharacterRepositoryImp): CharacterRepository

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideService(retrofit: Retrofit): CharacterService =
            retrofit.create(CharacterService::class.java)
    }

}
