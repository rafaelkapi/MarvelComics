package com.cactus.marvelcomics.features.comics_series.di

import com.cactus.marvelcomics.features.comics_series.domain.RequestComicsAndSeries
import com.cactus.marvelcomics.features.comics_series.domain.RequestComicsAndSeriesImp
import com.cactus.marvelcomics.features.comics_series.data.ComicsSeriesRepository
import com.cactus.marvelcomics.features.comics_series.data.ComicsSeriesRepositoryImp
import com.cactus.marvelcomics.features.details.data.ComisSeriesService
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
abstract class ComicsSeriesModule {

    @Binds
    abstract fun provideLocalDataSource(repository: ComicsSeriesRepositoryImp): ComicsSeriesRepository

    @Binds
    abstract fun provideRequestComicsAndSeries(requestComicsAndSeries: RequestComicsAndSeriesImp) : RequestComicsAndSeries

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideService(retrofit: Retrofit): ComisSeriesService =
            retrofit.create(ComisSeriesService::class.java)
    }

}
