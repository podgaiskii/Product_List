package com.example.productlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.productlist.MainActivity
import com.example.productlist.R
import com.example.productlist.base.BaseFragment
import com.example.productlist.databinding.FragmentMainBinding

class MainFragment : BaseFragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val host = childFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
        val navController = host.navController
        navController.graph = navController.navInflater.inflate(R.navigation.navigation_main)
        binding.bnvMain.setupWithNavController(host.navController)
    }
}