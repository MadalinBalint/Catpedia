package com.mendelin.catpedia.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mendelin.catpedia.R
import com.mendelin.catpedia.WelcomeScreenDataBinding
import com.mendelin.catpedia.constants.Status
import com.mendelin.catpedia.ui.custom_views.AlertBox
import com.mendelin.catpedia.di.viewmodels.ViewModelProviderFactory
import com.mendelin.catpedia.preferences.UserPreferences
import com.mendelin.catpedia.viewmodels.LoginViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_welcome_screen.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class WelcomeScreenActivity : DaggerAppCompatActivity(R.layout.activity_welcome_screen) {
    companion object {
        private const val SPLASH_TIME_OUT = 2000L
    }

    lateinit var binding: WelcomeScreenDataBinding

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome_screen)
        binding.userPreferences = UserPreferences

        if (UserPreferences.userIsLogged) {
            GlobalScope.launch {
                delay(SPLASH_TIME_OUT)
                loadMainScreen()
            }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                onBackPressed()
        }
        return true
    }

    private fun loginUser() {
        val name = editUserName.text.toString().trim()
        val password = editUserPassword.text.toString().trim()

        if (name.isEmpty() || password.isEmpty()) {
            showErrorAlert(this, getString(R.string.error_empty_field))
            return
        }

        val viewModel = ViewModelProvider(this, providerFactory).get(LoginViewModel::class.java)

        viewModel.loginUser(this, name, password).observe(this, {
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
                        showErrorAlert(
                            this, it.message
                                ?: getString(R.string.alert_error_unknown)
                        )
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
        val alert = AlertBox()

        alert.setPositiveButtonListener { dialog, _ ->
            dialog.dismiss()
        }

        alert.showAlert(
            context,
            context.getString(R.string.alert_error),
            msg,
            context.getString(R.string.alert_ok),
            null
        )
    }
}