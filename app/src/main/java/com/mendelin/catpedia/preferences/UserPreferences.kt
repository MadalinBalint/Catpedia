package com.mendelin.catpedia.preferences

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.mendelin.catpedia.models.UserObject

object UserPreferences {

    const val USER_LOGGED_IN = "USER_LOGGED_IN"

    const val USER_NAME = "USER_NAME"
    const val USER_EMAIL = "USER_EMAIL"
    const val USER_ACCESS_TOKEN = "USER_ACCESS_TOKEN"

    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        if (sharedPreferences == null) {
            val keySpec =
                KeyGenParameterSpec.Builder(MasterKey.DEFAULT_MASTER_KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setKeySize(256)
                    .build()

            val masterKeyAlias = MasterKey.Builder(context)
                .setKeyGenParameterSpec(keySpec)
                .build()

            sharedPreferences = EncryptedSharedPreferences.create(
                context,
                "PreferencesFilename",
                masterKeyAlias,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }
    }

    fun write(key: String, value: String) {
        sharedPreferences?.edit { putString(key, value) }
    }

    fun write(key: String, value: Boolean) {
        sharedPreferences?.edit { putBoolean(key, value) }
    }

    fun read(key: String, value: String): String? {
        return sharedPreferences?.getString(key, value)
    }

    fun read(key: String, value: Boolean): Boolean {
        return sharedPreferences?.getBoolean(key, value) ?: false
    }

    /* The user is logged in = TRUE, else FALSE */
    var userIsLogged: Boolean
        get() = read(USER_LOGGED_IN, false)
        set(value) = write(USER_LOGGED_IN, value)

    /* User data */
    var userName: String
        get() = read(USER_NAME, "") ?: ""
        set(value) = write(USER_NAME, value)

    var userEmail: String
        get() = read(USER_EMAIL, "") ?: ""
        set(value) = write(USER_EMAIL, value)

    var userAccessToken: String
        get() = read(USER_ACCESS_TOKEN, "") ?: ""
        set(value) = write(USER_ACCESS_TOKEN, value)

    fun logInUser(data: UserObject) {
        userIsLogged = true
        userName = data.user_name ?: ""
        userEmail = data.user_email ?: ""
        userAccessToken = data.access_token ?: ""
    }

    fun logOutUser() {
        userIsLogged = false

        userName = ""
        userEmail = ""
        userAccessToken = ""
    }
}