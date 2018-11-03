package com.nazer.saini.fcmapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Patterns
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import com.nazer.saini.fcmapp.R
import com.nazer.saini.fcmapp.callbacks.onTextChangeCallback
import java.io.IOException
import java.security.AccessControlContext
import java.text.SimpleDateFormat
import java.util.*

object CommonUtils {

    fun isInternetWorking(context: Context):Boolean
    {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    @SuppressLint("all")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun getTimestamp(): String {
        return SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT, Locale.US).format(Date())
    }



    fun openPlayStoreForApp(context: Context) {
        val appPackageName = context.packageName
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                            .resources
                            .getString(R.string.app_market_link) + appPackageName)))
        } catch (e: android.content.ActivityNotFoundException) {
            context.startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                            .resources
                            .getString(R.string.app_google_play_store_link) + appPackageName)))
        }

    }
fun onEditTextType(onTextChangeCallback: onTextChangeCallback):TextWatcher
{
  return  object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            onTextChangeCallback.afterTextChange(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

    }
}

}