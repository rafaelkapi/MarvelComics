package com.cactus.marvelcomics.di

import com.cactus.marvelcomics.ui.MainActivity
import com.cactus.marvelcomics.ui.character.di.CharacterModule
import com.cactus.marvelcomics.ui.character.di.CharacterModuleBuilder
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [
        CharacterModuleBuilder::class
        ])
    abstract fun bindMainActivity(): MainActivity

}
