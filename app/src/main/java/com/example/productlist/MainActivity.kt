package com.example.productlist

import android.view.View
import com.example.productlist.base.BaseActivity
import com.example.productlist.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    override val rootView: View
        get() = binding.root

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

}