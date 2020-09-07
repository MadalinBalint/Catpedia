package com.mendelin.catpedia.preferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import androidx.core.content.edit
import com.mendelin.catpedia.welcome_screen.bussiness_logic.models.UserObject

object UserPreferences {

    const val USER_LOGGED_IN = "USER_LOGGED_IN"

    const val USER_NAME = "USER_NAME"
    const val USER_EMAIL = "USER_EMAIL"
    const val USER_ACCESS_TOKEN = "USER_ACCESS_TOKEN"

    private var mSharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        if (mSharedPreferences == null) {
            mSharedPreferences =
                context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
        }
    }

    fun write(key: String, value: String) {
        mSharedPreferences?.edit { putString(key, value) }
    }

    fun write(key: String, value: Boolean) {
        mSharedPreferences?.edit { putBoolean(key, value) }
    }

    fun write(key: String, value: Int) {
        mSharedPreferences?.edit { putInt(key, value) }
    }

    fun write(key: String, value: Long) {
        mSharedPreferences?.edit { putLong(key, value).apply() }
    }

    fun write(key: String, value: Float) {
        mSharedPreferences?.edit { putFloat(key, value) }
    }

    fun write(key: String, value: ByteArray) {
        val stringValue = Base64.encodeToString(value, Base64.DEFAULT)
        write(key, stringValue)
    }

    fun read(key: String, value: String): String? {
        return mSharedPreferences?.getString(key, value)
    }

    fun read(key: String, value: Boolean): Boolean {
        return mSharedPreferences?.getBoolean(key, value) ?: false
    }

    fun read(key: String, value: Int): Int {
        return mSharedPreferences?.getInt(key, value) ?: 0
    }

    fun read(key: String, value: Long): Long {
        return mSharedPreferences?.getLong(key, value) ?: 0L
    }

    fun read(key: String, value: Float): Float {
        return mSharedPreferences?.getFloat(key, value) ?: 0f
    }

    fun read(key: String, value: ByteArray): ByteArray {
        var stringValue = ""
        if (value.isNotEmpty()) {
            stringValue = Base64.encodeToString(value, Base64.DEFAULT)
        }

        return Base64.decode(read(key, stringValue), Base64.DEFAULT)
    }

    fun removeValue(key: String) {
        mSharedPreferences?.edit { remove(key) }
    }

    fun clearAll() {
        mSharedPreferences?.edit { clear() }
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