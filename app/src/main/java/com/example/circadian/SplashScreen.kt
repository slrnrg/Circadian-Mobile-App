package com.example.circadian

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.content.ContextCompat

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // To change the status bar color
        window.statusBarColor = ContextCompat.getColor(this, R.color.primary_dark)

       // This handles the splash event
       // val welcomeScreen = findViewById<ImageView>(R.id.logo)
       // welcomeScreen.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_fade_in))
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, ScanQR::class.java)
            startActivity(intent)
            finish()
        }, 3000)

    }
}