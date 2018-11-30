package com.youlu.ads

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import com.youlu.ads.lockscreenad.LockScreenService.Companion.LOCK_SCREEN_ACTION
import com.youlu.ads.lockscreenad.LockScreenService.Companion.TAG
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*

class SplashActivity : AppCompatActivity() {


    var isJumpMain: Boolean = false

    companion object {

        const val KEY_TAG = ":jump_boolean"


        @JvmStatic
        fun start(context: Activity?) {
            context?.startActivity(Intent(context, SplashActivity::class.java))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        val intent = Intent()
        intent.action = LOCK_SCREEN_ACTION
        sendBroadcast(intent)
        super.onCreate(savedInstanceState)

        initWindow()
        setContentView(R.layout.activity_splash)

        isJumpMain = getIntent().getBooleanExtra(KEY_TAG, false)


        val countDownTimer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                if (isJumpMain) {
                    MainActivity.start(this@SplashActivity)
                    finish()
                }
            }

        }
        countDownTimer.start()


        slide_layout.setOnSlitherFinishListener {
            MainActivity.start(this@SplashActivity)
            finish()
        };
        slide_layout.setTouchView(window.decorView)
    }


    private fun initWindow() {
        //注意需要做一下判断
        if (window != null) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            // 锁屏的activity内部也要做相应的配置，让activity在锁屏时也能够显示，同时去掉系统锁屏。
            // 当然如果设置了系统锁屏密码，系统锁屏是没有办法去掉的
            // FLAG_DISMISS_KEYGUARD用于去掉系统锁屏页
            // FLAG_SHOW_WHEN_LOCKED使Activity在锁屏时仍然能够显示

            window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
            val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                keyguardManager.requestDismissKeyguard(this,object : KeyguardManager.KeyguardDismissCallback(){
                    override fun onDismissCancelled() {
                        super.onDismissCancelled()
                        Log.e(TAG,"onDismissCancelled")
                    }

                    override fun onDismissError() {
                        super.onDismissError()
                        Log.e(TAG,"onDismissError")
                    }
                })
            }

            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        // SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION，开发者容易被其中的HIDE_NAVIGATION所迷惑，
                        // 其实这个Flag没有隐藏导航栏的功能，只是控制导航栏浮在屏幕上层，不占据屏幕布局空间；
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        // SYSTEM_UI_FLAG_HIDE_NAVIGATION，才是能够隐藏导航栏的Flag；
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        // SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN，由上面可知，也不能隐藏状态栏，只是使状态栏浮在屏幕上层。
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_IMMERSIVE
            }
        }
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && window != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        // SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION，开发者容易被其中的HIDE_NAVIGATION所迷惑，
                        // 其实这个Flag没有隐藏导航栏的功能，只是控制导航栏浮在屏幕上层，不占据屏幕布局空间；
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        // SYSTEM_UI_FLAG_HIDE_NAVIGATION，才是能够隐藏导航栏的Flag；
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        // SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN，由上面可知，也不能隐藏状态栏，只是使状态栏浮在屏幕上层。
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_IMMERSIVE
            }
        }
    }


    override fun onBackPressed() {
        // 不做任何事，为了屏蔽back键
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val key = event.keyCode
        when (key) {
            KeyEvent.KEYCODE_BACK -> {
                return true
            }
            KeyEvent.KEYCODE_MENU -> {
                return true
            }
            else -> {
            }
        }
        return super.onKeyDown(keyCode, event)
    }


    override fun onDestroy() {
        val intent = Intent()
        intent.action = LOCK_SCREEN_ACTION
        sendBroadcast(intent)
        super.onDestroy()
    }


    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        val intent = Intent()
        intent.action = LOCK_SCREEN_ACTION
        sendBroadcast(intent)
        finish()
    }
}
