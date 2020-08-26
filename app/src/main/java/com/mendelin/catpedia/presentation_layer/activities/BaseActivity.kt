package com.mendelin.catpedia.presentation_layer.activities

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
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

    fun hideSoftKeyboard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        if (currentFocus != null && inputManager != null) {
            inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            inputManager.hideSoftInputFromInputMethod(currentFocus!!.windowToken, 0)
        }
    }
}