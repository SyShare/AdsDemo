package com.youlu.ads

import android.app.ActivityManager
import android.content.Context


/**
 * Author by Administrator , Date on 2018/11/6.
 * PS: Not easy to write code, please indicate.
 */

/**
 * 进入后台间隔10分钟以后可以再次显示广告
 *
 * @return 是否能显示广告
 */
fun canShowAd(sAppState: Int, backToFrontTime: Long, frontToBackTime: Long): Boolean {
    return sAppState == STATE_BACK_TO_FRONT && backToFrontTime - frontToBackTime >   1000
}


/**
 * 判断当前程序是否前台进程
 *
 * @param context
 * @return
 */
fun isCurAppTop(context: Context?): Boolean {
    if (context == null) {
        return false
    }
    val curPackageName = context.getPackageName()
    val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val list = am.getRunningTasks(1)
    if (list != null && list.size > 0) {
        val info = list[0]
        val topPackageName = info.topActivity.packageName
        val basePackageName = info.baseActivity.packageName
        if (topPackageName == curPackageName && basePackageName == curPackageName) {
            return true
        }
    }
    return false
}