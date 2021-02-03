package com.mendelin.catpedia.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.mendelin.catpedia.R
import com.mendelin.catpedia.WelcomeScreenBinding
import com.mendelin.catpedia.di.viewmodels.ViewModelProviderFactory
import com.mendelin.catpedia.preferences.UserPreferences
import com.mendelin.catpedia.ui.custom_views.AlertBox
import com.mendelin.catpedia.viewmodels.LoginViewModel
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class WelcomeScreenActivity : DaggerAppCompatActivity() {
    companion object {
        private const val SPLASH_TIME_OUT = 1500L
    }

    lateinit var binding: WelcomeScreenBinding

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private val viewModel: LoginViewModel by viewModels { providerFactory }

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome_screen)
        binding.userPreferences = UserPreferences

        if (UserPreferences.userIsLogged) {
            val scope = CoroutineScope(Dispatchers.Main.immediate)
            scope.launch {
                delay(SPLASH_TIME_OUT)
                loadMainScreen()
            }
        }

        /* Pressing Enter/Done on soft keyboard triggers the Login button */
        binding.editUserPassword.setOnEditorActionListener { _, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                binding.btnLogin.performClick()
            }
            false
        }

        binding.btnLogin.setOnClickListener {
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
        val name = binding.editUserName.text.toString().trim()
        val password = binding.editUserPassword.text.toString().trim()

        if (name.isEmpty() || password.isEmpty()) {
            showErrorAlert(this, getString(R.string.error_empty_field))
            return
        }

        disposables.add(
            viewModel.loginUser(this, name, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        val user = response.data
                        if (user != null) {
                            UserPreferences.logInUser(user)
                            loadMainScreen()
                        }
                    },
                    { throwable ->
                        showErrorAlert(this, throwable.message ?: "Unknown error")
                    }
                )
        )
    }

    private fun loadMainScreen() {
        val intent = Intent(this, CatpediaActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    private fun showErrorAlert(context: Context, msg: String) {
        AlertBox().apply {
            setPositiveButtonListener { dialog, _ ->
                dialog.dismiss()
            }

            showAlert(context, context.getString(R.string.alert_error), msg, context.getString(R.string.alert_ok), null)
        }
    }

    override fun onStop() {
        super.onStop()
        disposables.dispose()
    }
}