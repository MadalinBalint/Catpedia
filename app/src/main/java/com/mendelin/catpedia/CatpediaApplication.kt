package com.mendelin.catpedia

import com.mendelin.catpedia.di.DaggerAppComponent
import com.mendelin.catpedia.logging.TimberPlant
import com.mendelin.catpedia.preferences.UserPreferences
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class CatpediaApplication @Inject constructor() : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        UserPreferences.init(this)
        TimberPlant.plantTimberDebugLogger()
    }
}