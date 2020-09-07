package com.mendelin.catpedia.base_classes

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(private val layoutId: Int) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                onBackPressed()
        }
        return true
    }

    override fun onResume() {
        setAnimation()
        super.onResume()
    }

    override fun onPause() {
        setAnimation()
        super.onPause()
    }

    private fun setAnimation() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}