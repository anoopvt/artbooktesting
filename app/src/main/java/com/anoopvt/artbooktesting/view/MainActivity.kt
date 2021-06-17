package com.anoopvt.artbooktesting.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anoopvt.artbooktesting.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}