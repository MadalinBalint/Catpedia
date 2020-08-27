package com.mendelin.catpedia.presentation_layer.activities.welcome_screen.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProvider
import com.mendelin.catpedia.R
import com.mendelin.catpedia.common.ResourceUtils
import com.mendelin.catpedia.common.Status
import com.mendelin.catpedia.data_access_layer.preferences.UserPreferences
import com.mendelin.catpedia.presentation_layer.activities.BaseActivity
import com.mendelin.catpedia.presentation_layer.activities.main_screen.MainActivity
import com.mendelin.catpedia.presentation_layer.activities.welcome_screen.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_welcome_screen.*


class WelcomeScreenActivity : BaseActivity(R.layout.activity_welcome_screen) {
    companion object {
        private const val SPLASH_TIME_OUT = 2000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (UserPreferences.userIsLogged) {
            txtUserName.visibility = View.GONE
            txtUserPassword.visibility = View.GONE

            editUserName.visibility = View.GONE
            editUserPassword.visibility = View.GONE

            btnLogin.visibility = View.GONE

            txtAppDescription.visibility = View.VISIBLE

            Handler().postDelayed({
                loadMainScreen()
            }, SPLASH_TIME_OUT)
        } else
            txtAppDescription.visibility = View.GONE

        /* Pressing Enter/Done on soft keyboard triggers the Login button */
        editUserPassword.setOnEditorActionListener { _, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                btnLogin.performClick()
            }
            false
        }

        btnLogin.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val name = editUserName.text.toString().trim()
        val password = editUserPassword.text.toString().trim()

        if (name.isEmpty() || password.isEmpty()) {
            ResourceUtils.showErrorAlert(this, getString(R.string.error_empty_field))
            return
        }

        val viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        viewModel.loginUser(name, password).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        val response = it.data
                        UserPreferences.userIsLogged = true
                        UserPreferences.userName = response?.data?.user_name ?: ""
                        UserPreferences.userEmail = response?.data?.user_email ?: ""
                        UserPreferences.userAccessToken = response?.data?.access_token ?: ""
                        loadMainScreen()
                    }
                    Status.ERROR -> {
                        ResourceUtils.showErrorAlert(this, it.message
                            ?: getString(R.string.alert_error_unknown))
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }

    private fun loadMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }
}