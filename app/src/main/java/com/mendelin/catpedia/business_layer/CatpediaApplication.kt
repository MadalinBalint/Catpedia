package com.mendelin.catpedia.business_layer

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.mendelin.catpedia.business_layer.logging.TimberPlant
import com.mendelin.catpedia.data_access_layer.preferences.UserPreferences

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