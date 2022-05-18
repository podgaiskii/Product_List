package com.example.productlist.base

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    fun showError(@StringRes message: Int) {
        (activity as BaseActivity).showError(message)
    }

    fun showError(message: String) {
        (activity as BaseActivity).showError(message)
    }

}