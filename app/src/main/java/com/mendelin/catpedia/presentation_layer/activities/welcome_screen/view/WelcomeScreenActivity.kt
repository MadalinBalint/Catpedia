package com.mendelin.catpedia.presentation_layer.activities.welcome_screen.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import com.mendelin.catpedia.R
import com.mendelin.catpedia.common.Status
import com.mendelin.catpedia.data_access_layer.preferences.UserPreferences
import com.mendelin.catpedia.presentation_layer.MainActivity
import com.mendelin.catpedia.presentation_layer.activities.BaseActivity
import com.mendelin.catpedia.presentation_layer.activities.welcome_screen.bussiness_logic.viewmodel.LoginViewModel
import com.mendelin.catpedia.presentation_layer.custom_views.AlertBox
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

        btnLogin.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val name = editUserName.text.toString().trim()
        val password = editUserPassword.text.toString().trim()

        if (name.isEmpty() || password.isEmpty()) {
            showErrorAlert(this, "User name and password fields shouldn't be empty.")
            return
        }

        val viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        viewModel.loginUser(name, password).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        UserPreferences.userIsLogged = true
                        loadMainScreen()
                    }
                    Status.ERROR -> {
                        showErrorAlert(this, it.message
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

    private fun showErrorAlert(context: Context, msg: String) {
        val alert = AlertBox(context)

        alert.setPositiveButtonListener { dialog, _ ->
            dialog.dismiss()
        }

        alert.showAlert(
            getString(R.string.alert_error),
            msg,
            getString(R.string.alert_ok),
            null
        )
    }
}