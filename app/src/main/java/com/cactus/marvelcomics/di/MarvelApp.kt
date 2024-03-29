package com.cactus.marvelcomics.di

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MarvelApp : DaggerApplication() {

    lateinit var appComponent: AppComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {

        appComponent = DaggerAppComponent
            .factory()
            .create(this)

        appComponent.inject(this)

        return appComponent
    }
}