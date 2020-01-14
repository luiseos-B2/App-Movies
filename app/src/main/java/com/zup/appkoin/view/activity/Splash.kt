package com.zup.appkoin.view.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zup.appkoin.R
import kotlinx.android.synthetic.main.activity_splash.*

class Splash : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        animation_view.addAnimatorListener(object : AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                goMain()
                finish()
            }
        })
    }

    fun goMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
