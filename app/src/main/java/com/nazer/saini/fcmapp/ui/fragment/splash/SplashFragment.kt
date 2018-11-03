package com.nazer.saini.fcmapp.ui.fragment.splash

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nazer.saini.fcmapp.R
import com.nazer.saini.fcmapp.ui.base.BaseFragment
import com.nazer.saini.fcmapp.ui.fragment.home.HomeScreenFragment
import com.nazer.saini.fcmapp.ui.fragment.loginstartup.LoginStartUpFragment
import com.nazer.saini.fcmapp.util.ViewAnimationUtils
import kotlinx.android.synthetic.main.fragment_splash.view.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class SplashFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = LayoutInflater.from(activity).inflate(R.layout.fragment_splash, container, false)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Circular Reveal animation working Un comment and Run. ENJOY animation!
         */
        view.ll_main_color.setBackgroundColor(context!!.resources.getColor(R.color.splashGreencolor))
        // To run the animation as soon as the view is layout in the view hierarchy we add this
        // listener and remove it
        // as soon as it runs to prevent multiple animations if the view changes bounds
        ViewAnimationUtils.revealAnimation(view)
        printHashKey()
    }

    private fun printHashKey() {
        try {
            val info = context?.getPackageManager()?.getPackageInfo(
                    "com.nazer.saini.fcmapp",
                    PackageManager.GET_SIGNATURES)
            for (signature in info!!.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()

        } catch (e: NoSuchAlgorithmException) {

            e.printStackTrace()
        }

    }


    override fun setUpObservers() {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        countDownTimer.start()

    }

    var countDownTimer: CountDownTimer = object : CountDownTimer(3000, 1000) {
        override fun onFinish() {
            popFragment()
            if(getFirebaseAuthInstance().currentUser==null)onAddFragment(LoginStartUpFragment())else onAddFragment(HomeScreenFragment())
        }

        override fun onTick(millisUntilFinished: Long) {

        }

    }
}