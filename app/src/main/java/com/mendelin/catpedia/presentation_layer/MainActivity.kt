package com.mendelin.catpedia.presentation_layer

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mendelin.catpedia.R
import com.mendelin.catpedia.presentation_layer.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(R.layout.activity_main) {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(null)
        supportActionBar!!.setHomeButtonEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        navController = Navigation.findNavController(this, R.id.navHostFragment)
    }

    private fun setFragment(id: Int) {
        navController.navigate(id)
    }
}