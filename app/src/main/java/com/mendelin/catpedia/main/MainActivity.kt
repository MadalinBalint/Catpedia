package com.mendelin.catpedia.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.mendelin.catpedia.R
import com.mendelin.catpedia.custom_views.AlertBox
import com.mendelin.catpedia.preferences.UserPreferences
import com.mendelin.catpedia.welcome_screen.ui.WelcomeScreenActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : DaggerAppCompatActivity(R.layout.activity_main) {

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

            alert.showAlert(this,
                getString(R.string.alert_warning),
                getString(R.string.alert_logout),
                getString(R.string.alert_ok),
                getString(R.string.alert_cancel)
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                onBackPressed()
        }
        return true
    }
}