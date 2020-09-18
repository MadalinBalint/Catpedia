package com.mendelin.catpedia

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
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

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun attachBaseContext(base: Context) {
        UserPreferences.init(base)

        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()

        TimberPlant.plantTimberDebugLogger()
    }
}