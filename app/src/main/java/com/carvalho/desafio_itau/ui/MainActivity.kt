package com.carvalho.desafio_itau.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.carvalho.desafio_itau.MainViewModel
import com.carvalho.desafio_itau.R
import com.carvalho.desafio_itau.databinding.ActivityMainBinding
import com.carvalho.desafio_itau.repository.Repository
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setNavController()
        setupToolbar()

    }

    private fun setNavController() {
        navController = findNavController(R.id.fvMain)
        binding.toolbar.setupWithNavController(navController)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController)
        supportActionBar?.title = null
        binding.imLogo.setOnClickListener {
            val scroll = findViewById<NestedScrollView>(R.id.nsvScroll)
            scroll.scrollTo(0, 0)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}