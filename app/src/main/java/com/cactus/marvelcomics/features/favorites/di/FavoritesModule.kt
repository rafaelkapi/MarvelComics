package com.cactus.marvelcomics.features.favorites.di

import androidx.lifecycle.ViewModel
import com.cactus.marvelcomics.common.ViewModelKey
import com.cactus.marvelcomics.di.FragmentScope
import com.cactus.marvelcomics.features.characters.data.CharacterRepository
import com.cactus.marvelcomics.features.characters.data.CharacterRepositoryImp
import com.cactus.marvelcomics.features.characters.data.CharacterService
import com.cactus.marvelcomics.features.characters.presentation.CharactersFragment
import com.cactus.marvelcomics.features.characters.presentation.CharactersViewModel
import com.cactus.marvelcomics.features.comics_series.di.ComicsSeriesModule
import com.cactus.marvelcomics.features.favorites.data.FavoritesRepository
import com.cactus.marvelcomics.features.favorites.data.FavoritesRepositoryImp
import com.cactus.marvelcomics.features.favorites.presentation.FavoritesFragment
import com.cactus.marvelcomics.features.favorites.presentation.FavoritesViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module
abstract class FavoritesModuleBuilder {
    @FragmentScope
    @ContributesAndroidInjector(modules = [FavoritesDataModule::class, FavoritesModule::class])
    abstract fun bindsFavoritesFragment(): FavoritesFragment
}

@Module
abstract class FavoritesModule {

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    abstract fun bindFavoritesViewModel(viewModel: FavoritesViewModel): ViewModel

}

@Module
abstract class FavoritesDataModule {

    @Binds
    abstract fun provideLocalDataSource(repository: FavoritesRepositoryImp): FavoritesRepository



}
