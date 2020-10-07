package com.mendelin.catpedia.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import com.mendelin.catpedia.R
import com.mendelin.catpedia.di.viewmodels.ViewModelProviderFactory
import com.mendelin.catpedia.preferences.UserPreferences
import com.mendelin.catpedia.ui.custom_views.AlertBox
import com.mendelin.catpedia.viewmodels.BreedsViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity(R.layout.activity_main), ActivityCallback {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private val breedsViewModel: BreedsViewModel by viewModels { providerFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)

        supportActionBar?.let {
            with(it) {
                setDisplayShowTitleEnabled(false)
                setHomeAsUpIndicator(null)
                setHomeButtonEnabled(false)
                setDisplayHomeAsUpEnabled(false)
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

        btnLogout.setOnClickListener {
            val alert = AlertBox()

            alert.setPositiveButtonListener { _, _ ->
                UserPreferences.logOutUser()

                val intent = Intent(this, WelcomeScreenActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }

            alert.setNegativeButtonListener { dialog, _ ->
                dialog.dismiss()
            }

            alert.showAlert(
                this,
                getString(R.string.alert_warning),
                getString(R.string.alert_logout),
                getString(R.string.alert_ok),
                getString(R.string.alert_cancel)
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun showSearchBar(isShow: Boolean) {
        searchView.visibility = if (isShow) View.VISIBLE else View.GONE
    }
}

interface ActivityCallback {
    fun showSearchBar(isShow: Boolean)
}