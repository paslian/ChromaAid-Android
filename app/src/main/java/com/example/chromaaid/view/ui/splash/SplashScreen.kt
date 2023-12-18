package com.example.chromaaid.view.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.example.chromaaid.R
import com.example.chromaaid.view.ui.Main.MainActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        setContentView(R.layout.activity_splash_screen)
        val splashLogo = findViewById<ImageView>(R.id.splash_logo)

        splashLogo.alpha = 0f
        splashLogo.animate().setDuration(ANIMATION_DURATION.toLong()).alpha(1f).withEndAction{
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }
    companion object {
        const val ANIMATION_DURATION = 1500
    }
}