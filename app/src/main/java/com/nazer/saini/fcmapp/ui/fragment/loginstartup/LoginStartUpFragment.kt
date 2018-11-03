package com.nazer.saini.fcmapp.ui.fragment.loginstartup

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.CallbackManager
import com.facebook.FacebookSdk
import com.nazer.saini.fcmapp.R
import com.nazer.saini.fcmapp.callbacks.GenericApiCallback
import com.nazer.saini.fcmapp.databinding.FragmentLoginStartupBinding
import com.nazer.saini.fcmapp.manager.fbmanager.FacebookManager
import com.nazer.saini.fcmapp.pojo.FacebookPojo
import com.nazer.saini.fcmapp.ui.activity.MainActivity
import com.nazer.saini.fcmapp.ui.base.BaseFragment
import com.nazer.saini.fcmapp.ui.fragment.home.HomeScreenFragment
import com.nazer.saini.fcmapp.ui.fragment.signup.email.SignUpFragment
import com.nazer.saini.fcmapp.util.PrintLog
import com.nazer.saini.fcmapp.ui.fragment.login.LoginFragment
import com.nazer.saini.fcmapp.ui.fragment.signup.mobileverification.MobileVerificationFragment


class LoginStartUpFragment : BaseFragment(), LoginStartUpViewModel.loginStartup {


    private lateinit var callbackManager: CallbackManager
    private var binding: FragmentLoginStartupBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_startup, container, false)
        FacebookSdk.sdkInitialize(context)
        callbackManager = CallbackManager.Factory.create();
        setDataonView()
        setUpObservers()

        return binding?.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PrintLog.e("user is ", "" + getFirebaseAuthInstance().currentUser?.email)
    }

    fun setDataonView() {
        binding?.fragment = this
    }

    override fun onLoginWithEmailClick(view: View) = onAddFragment(LoginFragment())

    override fun onEmailRegisterClick(view: View) = onAddFragment(SignUpFragment())

    override fun onMobileRegisterClick(view: View) = onAddFragment(MobileVerificationFragment())

    override fun onfacebookClick(view: View) {

        FacebookManager.fbLogin(context as MainActivity, callbackManager, object : GenericApiCallback<FacebookPojo> {
            override fun onSuccess(t: FacebookPojo) = PrintLog.e("onFb login ", t.token?.token.toString())
            override fun onerror(error: Throwable) = PrintLog.e("onFb login ", error.message.toString())
        }, getFirebaseAuthInstance())
    }

    override fun setUpObservers() {

    }

    override fun onGmailClick(view: View) {
    }

    override fun onTwitterClick(view: View) {
    }

    override fun onLinkedlnClick(view: View) {
    }

    override fun onSuccessfullyLogin(view: View) {
        clearBackStack()
        onAddFragment(HomeScreenFragment())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }


}

