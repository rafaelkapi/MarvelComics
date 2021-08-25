package com.cactus.marvelcomics.di

import com.cactus.marvelcomics.features.MainActivity
import com.cactus.marvelcomics.features.characters.di.CharacterModuleBuilder
import com.cactus.marvelcomics.features.comics_series.di.ComicsSeriesModule
import com.cactus.marvelcomics.features.details.di.DetailsModuleBuilder
import com.cactus.marvelcomics.features.favorites.di.FavoritesModuleBuilder
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [
        CharacterModuleBuilder::class,
        DetailsModuleBuilder::class,
        FavoritesModuleBuilder::class,
        ComicsSeriesModule::class,
        ])
    abstract fun bindMainActivity(): MainActivity

}
