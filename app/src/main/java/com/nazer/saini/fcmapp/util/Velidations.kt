package com.nazer.saini.fcmapp.util

import android.util.Patterns
import java.util.regex.Pattern

object Velidations {


//    fun isValidateEmail(email: String): Boolean {
//        val EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$"
//        val pattern = Pattern.compile(EMAIL_PATTERN)
//        val matcher = pattern.matcher(email)
//        return matcher.matches()
//    }
fun String.isValidateEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


}