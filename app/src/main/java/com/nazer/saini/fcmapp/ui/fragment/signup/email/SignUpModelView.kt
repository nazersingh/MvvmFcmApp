package com.nazer.saini.fcmapp.ui.fragment.signup.email

import android.text.TextWatcher
import android.view.View
import com.nazer.saini.fcmapp.R
import com.nazer.saini.fcmapp.application.MyApplication
import com.nazer.saini.fcmapp.callbacks.GenericCallBack
import com.nazer.saini.fcmapp.callbacks.onTextChangeCallback
import com.nazer.saini.fcmapp.pojo.Android
import com.nazer.saini.fcmapp.ui.base.BaseModel
import com.nazer.saini.fcmapp.ui.base.BaseObserver
import com.nazer.saini.fcmapp.ui.base.FcmModel
import com.nazer.saini.fcmapp.util.CommonUtils
import com.nazer.saini.fcmapp.util.PrintLog
import com.nazer.saini.fcmapp.util.Velidations
import java.util.*
import javax.inject.Inject

class SignUpModelView(var callBack: BaseObserver, var signUpCallback: CallBackSignUp)  : Observable() {
    /**
     * https://www.azoft.com/blog/mvvm-android-data-binding/
     */

    @Inject
    lateinit var baseModel: BaseModel
    @Inject
    lateinit var fcmModel: FcmModel

    private val TAG: String = this.javaClass.simpleName

    var email: String = ""
    var password: String = ""
    var confirmPassword: String = ""

    fun getEmailTextWatcher(): TextWatcher {
        return CommonUtils.onEditTextType(object : onTextChangeCallback {
            override fun afterTextChange(str: String) {
                email = str
            }
        })
    }

    fun getPasswordTextWatcher(): TextWatcher {
        return CommonUtils.onEditTextType(object : onTextChangeCallback {
            override fun afterTextChange(str: String) {
                password = str
            }
        })
    }fun getConfirmPasswordTextWatcher(): TextWatcher {
        return CommonUtils.onEditTextType(object : onTextChangeCallback {
            override fun afterTextChange(str: String) {
                confirmPassword = str
            }
        })
    }

    init {
        MyApplication.getAppInstance().component.signup(this)
    }


    fun callSignUpApi(hashMap: HashMap<Any, Any>) {
        callBack.addDisposal(baseModel.callLoginApi(hashMap, object : GenericCallBack<Android> {
            override fun onSubscribe() {
                callBack.showLoader()
            }

            override fun onSuccess(t: Android) {
                callBack.hideLoader()

                // when using observable
                setChanged()
                notifyObservers()
            }

            override fun onerror(error: Throwable) {
                callBack.hideLoader()
            }
        }))
    }


    fun callSignUp(view: View) {
        PrintLog.e(TAG, " on click" + email)

        if (email.isEmpty())
            signUpCallback.onEmailError(MyApplication.getAppInstance().getString(R.string.err_enter_email))
        else if (!Velidations.isEmailValid(email))
            signUpCallback.onEmailError(MyApplication.getAppInstance().getString(R.string.err_valid_email))
        else if (password.isEmpty())
            signUpCallback.onPasswordError(MyApplication.getAppInstance().getString(R.string.err_enter_password))
        else
            callSignUpApi(HashMap())
//            fcmModel.signInWithCredential(EmailAuthProvider.getCredential(email, password), object : GenericApiCallback<String> {
//                override fun onSuccess(t: String) {
//                    signUpCallback.onSuccessfullyLogin()
//                }
//
//                override fun onerror(error: Throwable) {
//                    signUpCallback.onErrorLogin(error.message!!)
//                }
//            })
    }

    fun signIn(view: View) {
        signUpCallback.onSignIn()
    }

    interface CallBackSignUp {
        fun onEmailError(emailError: String)
        fun onPasswordError(passwordError: String)
        fun onConfirmPasswordError(passwordError: String)
        fun onSignIn()
        fun onSuccessfullySignUp()
        fun onSignUpError(errorMassege: String)
    }
}