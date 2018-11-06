package com.youlu.ads

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks2
import android.content.ComponentCallbacks2.TRIM_MEMORY_BACKGROUND
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log

/**
 * Author by Administrator , Date on 2018/11/6.
 * PS: Not easy to write code, please indicate.
 */

// 正常状态
public const val STATE_NORMAL = 0
// 从后台回到前台
public const val STATE_BACK_TO_FRONT = 1
// 从前台进入后台
public const val STATE_FRONT_TO_BACK = 2

class SplashWrapper(application: FrameApplication) : Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {

    // APP状态
    private var sAppState = STATE_NORMAL
    // 标记程序是否已进入后台(依据onStop回调)
    private var flag: Boolean = false
    // 标记程序是否已进入后台(依据onTrimMemory回调)
    private var background: Boolean = false
    // 从前台进入后台的时间
    private var frontToBackTime: Long = 0
    // 从后台返回前台的时间
    private var backToFrontTime: Long = 0

    private val mApplication = application


    init {
        mApplication.registerActivityLifecycleCallbacks(this)
        mApplication.registerComponentCallbacks(this)
    }

    override fun onLowMemory() {
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
    }

    override fun onActivityPaused(activity: Activity?) {
    }

    private  val TAG: String = SplashWrapper::class.java.simpleName

    override fun onActivityResumed(activity: Activity?) {
        if (background || flag) {
            background = false;
            flag = false;
            sAppState = STATE_BACK_TO_FRONT;
            backToFrontTime = System.currentTimeMillis();
            Log.e(TAG, "onResume: STATE_BACK_TO_FRONT");
            if (canShowAd(sAppState,backToFrontTime,frontToBackTime)) {
                SplashActivity.start(activity)
            }
        } else {
            sAppState = STATE_NORMAL;
        }
    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
        //判断当前activity是否处于前台
        if (!isCurAppTop(activity)) {
            // 从前台进入后台
            sAppState = STATE_FRONT_TO_BACK;
            frontToBackTime = System.currentTimeMillis()
            flag = true;
            Log.e(TAG, "onStop: " + "STATE_FRONT_TO_BACK")
        } else {
            // 否则是正常状态
            sAppState = STATE_NORMAL;
        }
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
    }

    override fun onTrimMemory(level: Int) {
        // TRIM_MEMORY_UI_HIDDEN是UI不可见的回调, 通常程序进入后台后都会触发此回调,大部分手机多是回调这个参数
        // TRIM_MEMORY_BACKGROUND也是程序进入后台的回调, 不同厂商不太一样, 魅族手机就是回调这个参数
        if (level == Application.TRIM_MEMORY_UI_HIDDEN || level == TRIM_MEMORY_BACKGROUND) {
            background = true
        } else if (level == Application.TRIM_MEMORY_COMPLETE) {
            background = !isCurAppTop(mApplication)
        }
        if (background) {
            frontToBackTime = System.currentTimeMillis()
            sAppState = STATE_FRONT_TO_BACK
            Log.e(TAG, "onTrimMemory: TRIM_MEMORY_UI_HIDDEN || TRIM_MEMORY_BACKGROUND");
        } else {
            sAppState = STATE_NORMAL
        }
    }
}