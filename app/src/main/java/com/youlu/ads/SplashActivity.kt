package com.youlu.ads

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {


    companion object {
        @JvmStatic
        fun start(context: Activity?) {
            context?.startActivity(Intent(context, SplashActivity::class.java))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val countDownTimer = object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                MainActivity.start(this@SplashActivity)
                finish()
            }

        }
        countDownTimer.start()
    }
}
