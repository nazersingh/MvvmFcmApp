package com.nazer.saini.fcmapp.ui.fragment.signup.email

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nazer.saini.fcmapp.R
import com.nazer.saini.fcmapp.databinding.FragmentSignupBinding
import com.nazer.saini.fcmapp.ui.base.BaseFragment
import com.nazer.saini.fcmapp.ui.base.BaseObserver
import io.reactivex.disposables.Disposable
import java.util.*

class SignUpFragment:BaseFragment(), BaseObserver, SignUpModelView.CallBackSignUp {

    private var signUpViewModel = SignUpModelView(this, this)
    private var binding: FragmentSignupBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)
        setDataonView()
        setUpObservers()
        return binding?.root
    }

    fun setDataonView() {
        binding?.signup = signUpViewModel
    }


    override fun addDisposal(disposable: Disposable) {
       addDisposal(disposable)
    }

    override fun update(o: Observable?, arg: Any?) {
    }

    override fun onEmailError(emailError: String) {
    }

    override fun onPasswordError(passwordError: String) {
    }

    override fun onConfirmPasswordError(passwordError: String) {
    }

    override fun onSignIn() {
        popFragment()
    }

    override fun onSuccessfullySignUp() {
    }

    override fun onSignUpError(errorMassege: String) {
    }

    override fun setUpObservers() {
        signUpViewModel.addObserver(this)
    }

}