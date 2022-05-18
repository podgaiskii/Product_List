package com.example.productlist.base

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.example.productlist.util.SnackbarUtil
import com.google.android.material.snackbar.BaseTransientBottomBar

abstract class BaseActivity : AppCompatActivity() {

    protected abstract val rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(rootView)
    }

    fun showError(@StringRes message: Int) {
        SnackbarUtil.showSnackbar(
            rootView,
            this,
            getString(message),
            BaseTransientBottomBar.LENGTH_LONG
        )
    }

    fun showError(message: String) {
        SnackbarUtil.showSnackbar(
            rootView,
            this,
            message,
            BaseTransientBottomBar.LENGTH_LONG
        )
    }
}