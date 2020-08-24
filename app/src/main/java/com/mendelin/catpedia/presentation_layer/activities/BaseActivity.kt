package com.mendelin.catpedia.presentation_layer.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
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

    fun closeActivity(key: String? = null, value: String? = null) {
        val intentLogin = Intent()
        if (!key.isNullOrEmpty() && !value.isNullOrEmpty()) {
            intentLogin.putExtra(key, value)
        }
        setResult(Activity.RESULT_OK, intentLogin)
        finish()
    }
}