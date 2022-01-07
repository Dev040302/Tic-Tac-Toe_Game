package com.dev.tttonlinemultiplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.dev.tttonlinemultiplayer.databinding.SplashBinding

class Splash : AppCompatActivity() {

    lateinit var binding: SplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashBinding.inflate(layoutInflater)

        var animation: android.view.animation.Animation
        var animation1: android.view.animation.Animation
        var animation2: android.view.animation.Animation
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein)
        animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein1)
        animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein2)

        binding.tic.startAnimation(animation)
        binding.tac.startAnimation(animation1)
        binding.toe.startAnimation(animation2)

        Handler().postDelayed({
            startActivity(Intent(this@Splash, MainActivity::class.java))
            finish()
        }, 6000)

        setContentView(binding.root)

    }
}