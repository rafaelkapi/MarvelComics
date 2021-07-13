package com.cactus.marvelcomics.ui.character.di

import androidx.lifecycle.ViewModel
import com.cactus.marvelcomics.common.ViewModelKey
import com.cactus.marvelcomics.common.base.BaseViewModel
import com.cactus.marvelcomics.di.FragmentScope
import com.cactus.marvelcomics.ui.character.presentation.CharactersFragment
import com.cactus.marvelcomics.ui.character.presentation.CharactersViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class CharacterModuleBuilder {
    @FragmentScope
    @ContributesAndroidInjector(modules = [DataModule::class, CharacterModule::class])
    abstract fun bindsCharactersFragment(): CharactersFragment
}

@Module
abstract class CharacterModule {

    @Binds
    @IntoMap
    @ViewModelKey(CharactersViewModel::class)
    abstract fun bindCharactersViewModel(viewModel: CharactersViewModel): ViewModel

}
