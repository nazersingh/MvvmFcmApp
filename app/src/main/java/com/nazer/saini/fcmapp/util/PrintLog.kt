package com.nazer.saini.fcmapp.util

import android.util.Log
import com.nazer.saini.fcmapp.BuildConfig

object PrintLog {

    private val isDebug = BuildConfig.DEBUG

    fun e(TAG: String, msg: String) {
        if (isDebug)
            Log.e(TAG, msg)
    }

    fun i(TAG: String, msg: String) {
        if (isDebug)
            Log.i(TAG, msg)
    }

    fun d(TAG: String, msg: String) {
        if (isDebug)
            Log.d(TAG, msg)
    }

}