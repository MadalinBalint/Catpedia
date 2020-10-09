package com.mendelin.catpedia.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import com.mendelin.catpedia.R
import com.mendelin.catpedia.databinding.ActivityMainBinding
import com.mendelin.catpedia.di.viewmodels.ViewModelProviderFactory
import com.mendelin.catpedia.preferences.UserPreferences
import com.mendelin.catpedia.ui.custom_views.AlertBox
import com.mendelin.catpedia.viewmodels.BreedsViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity(), ActivityCallback {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private lateinit var binding: ActivityMainBinding

    private val breedsViewModel: BreedsViewModel by viewModels { providerFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.let {
            with(it) {
                setDisplayShowTitleEnabled(false)
                setHomeAsUpIndicator(null)
                setHomeButtonEnabled(false)
                setDisplayHomeAsUpEnabled(false)
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                breedsViewModel.filter(query?.trim() ?: "")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isEmpty() == true) {
                    breedsViewModel.filter("")
                }
                return false
            }
        })

        binding.btnLogout.setOnClickListener {
            val context = this@MainActivity
            AlertBox().apply {
                setPositiveButtonListener { _, _ ->
                    UserPreferences.logOutUser()
                    loadWelcomeScreen()
                }

                setNegativeButtonListener { dialog, _ ->
                    dialog.dismiss()
                }

                showAlert(
                    context,
                    context.getString(R.string.alert_warning),
                    context.getString(R.string.alert_logout),
                    context.getString(R.string.alert_ok),
                    context.getString(R.string.alert_cancel)
                )
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun loadWelcomeScreen() {
        val intent = Intent(this, WelcomeScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    override fun showSearchBar(isShown: Boolean) {
        binding.searchView.visibility = if (isShown) View.VISIBLE else View.GONE
    }

    override fun showToolbar(isShown: Boolean) {
        binding.toolbar.visibility = if (isShown) View.VISIBLE else View.GONE
    }
}

interface ActivityCallback {
    fun showSearchBar(isShown: Boolean)
    fun showToolbar(isShown: Boolean)
}