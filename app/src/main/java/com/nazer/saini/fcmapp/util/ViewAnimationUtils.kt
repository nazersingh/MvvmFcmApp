package com.nazer.saini.fcmapp.util

import android.os.Build
import android.support.annotation.RequiresApi
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.ScaleAnimation
import kotlinx.android.synthetic.main.fragment_splash.view.*

object ViewAnimationUtils {

    fun scaleAnimateView(view: View) {
        val animation = ScaleAnimation(
                1.15f, 1f, 1.15f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f)

        view.animation = animation
        animation.duration = 100
        animation.start()
    }

    fun revealAnimation(view: View) {
        view.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int,
                                        oldRight: Int, oldBottom: Int) {
                v.removeOnLayoutChangeListener(this)
//                val cx = arguments!!.getInt("cx")
                val cx = view.ll_main_color.x.toInt()
                val cy = view.ll_main_color.y.toInt()

                // get the hypothenuse so the radius is from one corner to the other
                val radius = Math.hypot(right.toDouble(), bottom.toDouble()).toInt()

                val reveal = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0f, radius.toFloat())
                reveal.interpolator = DecelerateInterpolator(2f)
                reveal.duration = 1000
                reveal.start()
            }
        })
    }
}