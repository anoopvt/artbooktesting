package com.anoopvt.artbooktesting.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.anoopvt.artbooktesting.R
import com.anoopvt.artbooktesting.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel: ArtViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.art_fragment_container) as? NavHostFragment
        val navController = navHostFragment?.navController
        val navInflater = navController?.navInflater
        val graph = navInflater?.inflate(R.navigation.nav_graph)

        viewModel.token.observe(this, {
            if (it == null) {
                graph?.startDestination = R.id.loginFragment
            }
            else{
                graph?.startDestination = R.id.artFragment
            }
            graph?.let {
                navController.graph = graph
            }
        })

    }
}