package com.nazer.saini.fcmapp.ui.fragment.loginstartup

import android.view.View

class LoginStartUpViewModel(loginStartUpCallback: loginStartup) {

    interface loginStartup{
        fun onLoginWithEmailClick(view: View)
        fun onEmailRegisterClick(view: View)
        fun onMobileRegisterClick(view:View)
        fun onfacebookClick(view:View)
        fun onGmailClick(view:View)
        fun onTwitterClick(view:View)
        fun onLinkedlnClick(view:View)
        fun onSuccessfullyLogin(view:View)

    }
}