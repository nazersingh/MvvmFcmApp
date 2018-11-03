package com.nazer.saini.fcmapp.ui.fragment.login

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nazer.saini.fcmapp.R
import com.nazer.saini.fcmapp.databinding.FragmentLoginBinding
import com.nazer.saini.fcmapp.ui.base.BaseFragment
import com.nazer.saini.fcmapp.ui.base.BaseObserver
import com.nazer.saini.fcmapp.ui.dialogues.Dialogues
import com.nazer.saini.fcmapp.ui.fragment.forgetpassword.ForgetPasswordFragment
import com.nazer.saini.fcmapp.ui.fragment.home.HomeScreenFragment
import com.nazer.saini.fcmapp.ui.fragment.signup.email.SignUpFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*


class LoginFragment : BaseFragment(), BaseObserver, LoginViewModel.CallBackLogin {


    private var loginViewModel = LoginViewModel(this, this)
    private var binding: FragmentLoginBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        setDataonView()
        setUpObservers()
        return binding?.root
    }

    fun setDataonView() {
        binding?.loginData = loginViewModel
    }

    override fun update(o: Observable?, arg: Any?) {

        if (o is LoginViewModel)
            setDataonView()
    }

    override fun setUpObservers() {
        loginViewModel.addObserver(this)
    }

    override fun showLoader() {
        binding?.progress?.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        binding?.progress?.visibility = View.GONE
    }

    override fun addDisposal(disposable: Disposable) {
        addDisposal(disposable)
    }

    override fun onEmailError(emailError: String) = showToast(emailError)

    override fun onPasswordError(passwordError: String) = showToast(passwordError)


    override fun onForgotPassword() {
        onAddFragment(ForgetPasswordFragment())
    }


    override fun onSignUp() = onAddFragment(SignUpFragment())


    override fun onSuccessfullyLogin() {
        clearBackStack()
        onAddFragment(HomeScreenFragment())
    }


    override fun onErrorLogin(errorMassege: String) {
        Dialogues.setMessage(errorMassege)
                .setPositiveButtonText(Dialogues.POSITIVE_BUTTON_OK)
                .isCancelAble(false)
                .showDialogueOnlyText(activity!!)
    }
}


