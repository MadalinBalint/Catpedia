package com.mendelin.catpedia.main_screen

import android.content.Intent
import android.os.Bundle
import com.mendelin.catpedia.R
import com.mendelin.catpedia.base_classes.BaseActivity
import com.mendelin.catpedia.custom_views.AlertBox
import com.mendelin.catpedia.preferences.UserPreferences
import com.mendelin.catpedia.welcome_screen.ui.WelcomeScreenActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(null)
        supportActionBar!!.setHomeButtonEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)

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
}