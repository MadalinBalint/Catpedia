package com.mendelin.catpedia.presentation_layer.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.mendelin.catpedia.R
import com.mendelin.catpedia.data_access_layer.preferences.UserPreferences
import com.mendelin.catpedia.presentation_layer.MainActivity
import kotlinx.android.synthetic.main.activity_welcome_screen.*

class WelcomeScreenActivity : BaseActivity(R.layout.activity_welcome_screen) {
    companion object {
        private const val SPLASH_TIME_OUT = 2000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (UserPreferences.userIsLogged) {
            txtUserEmail.visibility = View.GONE
            txtUserPassword.visibility = View.GONE

            editUserEmail.visibility = View.GONE
            editUserPassword.visibility = View.GONE

            btnLogin.visibility = View.GONE

            Handler().postDelayed({
                loginUser()
            }, SPLASH_TIME_OUT)
        }

        btnLogin.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        UserPreferences.userIsLogged = true

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }
}