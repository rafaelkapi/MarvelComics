package com.cactus.marvelcomics.di

import android.content.Context
import com.cactus.marvelcomics.MarvelApp
import com.cactus.marvelcomics.common.ViewModelFactoryModule
import com.cactus.marvelcomics.data.db.dbModule
import com.cactus.marvelcomics.data.network.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ViewModelFactoryModule::class,
    ActivityBuilderModule::class,
    dbModule::class,
    NetworkModule::class])
interface AppComponent : AndroidInjector<DaggerApplication> {

//    fun inject(application: MarvelApp)

    override fun inject(instance: DaggerApplication)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

}

