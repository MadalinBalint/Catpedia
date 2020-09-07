package com.mendelin.catpedia

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.mendelin.catpedia.logging.TimberPlant
import com.mendelin.catpedia.preferences.UserPreferences

class CatpediaApplication : Application() {

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun attachBaseContext(base: Context) {
        UserPreferences.init(base)

        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()

        if (instance == null) {
            instance = this
        }

        TimberPlant.plantTimberDebugLogger()
    }

    companion object {
        private var instance: CatpediaApplication? = null

        fun getInstance(): CatpediaApplication = instance!!
    }
}