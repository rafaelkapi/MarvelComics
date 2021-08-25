package com.cactus.marvelcomics.features.details.di

import androidx.lifecycle.ViewModel
import com.cactus.marvelcomics.common.ViewModelKey
import com.cactus.marvelcomics.di.FragmentScope
import com.cactus.marvelcomics.features.comics_series.di.ComicsSeriesModule
import com.cactus.marvelcomics.features.details.presentation.DetailsFragment
import com.cactus.marvelcomics.features.details.presentation.DetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class DetailsModuleBuilder {
    @FragmentScope
    @ContributesAndroidInjector(modules = [DetailsModule::class])
    abstract fun bindsDetailsFragment(): DetailsFragment
}

@Module
abstract class DetailsModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindDetailsViewModel(viewModel: DetailsViewModel): ViewModel

}
