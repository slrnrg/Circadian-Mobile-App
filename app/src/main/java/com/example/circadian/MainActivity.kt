package com.example.circadian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // To change the status bar color
        window.statusBarColor = ContextCompat.getColor(this, R.color.primary_dark)

        // Bottom navigation set up
        bottom_navigation.setupWithNavController(navHostFragment.findNavController())
    }
}