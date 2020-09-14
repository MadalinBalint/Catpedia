package com.mendelin.catpedia.welcome_screen.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.mendelin.catpedia.R
import com.mendelin.catpedia.WelcomeScreenBinding
import com.mendelin.catpedia.base_classes.BaseActivity
import com.mendelin.catpedia.constants.Status
import com.mendelin.catpedia.main_screen.MainActivity
import com.mendelin.catpedia.preferences.UserPreferences
import com.mendelin.catpedia.utils.ResourceUtils
import com.mendelin.catpedia.welcome_screen.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_welcome_screen.*


class WelcomeScreenActivity : BaseActivity(R.layout.activity_welcome_screen) {
    companion object {
        private const val SPLASH_TIME_OUT = 2000L
    }

    lateinit var binding: WelcomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome_screen)
        binding.userPreferences = UserPreferences

        if (UserPreferences.userIsLogged) {
            Handler().postDelayed({
                loadMainScreen()
            }, SPLASH_TIME_OUT)
        }

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

        val viewModel: LoginViewModel by viewModels()

        viewModel.loginUser(name, password).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        val response = it.data
                        if (response?.data != null) {
                            UserPreferences.logInUser(response.data!!)
                            loadMainScreen()
                        }
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