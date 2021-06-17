package com.anoopvt.artbooktesting.view

import android.content.Context
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtNavHostFragment :NavHostFragment(){

    @Inject
    lateinit var artFragmentFactory: ArtFragmentFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        childFragmentManager.fragmentFactory = artFragmentFactory
    }
}