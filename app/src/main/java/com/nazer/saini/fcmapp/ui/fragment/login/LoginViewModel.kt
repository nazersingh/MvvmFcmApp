package com.nazer.saini.fcmapp.ui.fragment.login

import android.arch.lifecycle.MutableLiveData
import android.os.Message
import android.view.View
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap
import android.text.TextWatcher
import com.google.firebase.auth.EmailAuthProvider
import com.nazer.saini.fcmapp.R
import com.nazer.saini.fcmapp.application.MyApplication
import com.nazer.saini.fcmapp.callbacks.GenericApiCallback
import com.nazer.saini.fcmapp.callbacks.GenericCallBack
import com.nazer.saini.fcmapp.callbacks.onTextChangeCallback
import com.nazer.saini.fcmapp.pojo.Android
import com.nazer.saini.fcmapp.pojo.CharacterModel
import com.nazer.saini.fcmapp.ui.base.BaseModel
import com.nazer.saini.fcmapp.ui.base.BaseObserver
import com.nazer.saini.fcmapp.ui.base.FcmModel
import com.nazer.saini.fcmapp.util.CommonUtils
import com.nazer.saini.fcmapp.util.PrintLog
import com.nazer.saini.fcmapp.util.Velidations


class LoginViewModel(var callBack: BaseObserver, var loginCallback: CallBackLogin) : Observable() {
    /**
     * https://www.azoft.com/blog/mvvm-android-data-binding/
     */

    @Inject
    lateinit var baseModel: BaseModel
    @Inject
    lateinit var fcmModel: FcmModel

    var androidList = MutableLiveData<List<CharacterModel>>()
    var androidResponse = MutableLiveData<Android>()
    private val TAG: String = this.javaClass.simpleName

    var email: String = ""
    var password: String = ""

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
    }

    init {
        MyApplication.getAppInstance().component.login(this)
    }


    fun callLoginApi(hashMap: HashMap<Any, Any>) {
        callBack.addDisposal(baseModel.callLoginApi(hashMap, object : GenericCallBack<Android> {
            override fun onSubscribe() {
                callBack.showLoader()
            }

            override fun onSuccess(t: Android) {
                callBack.hideLoader()
                androidResponse.value = t
                // when using observable
                setChanged()
                notifyObservers()
            }

            override fun onerror(error: Throwable) {
                callBack.hideLoader()
            }
        }))
    }


    fun callLogin(view: View) {
        PrintLog.e(TAG, " on click" + email)

        if(email.isEmpty())
        loginCallback.onEmailError(MyApplication.getAppInstance().getString(R.string.err_enter_email))
        else if(!Velidations.isEmailValid(email))
            loginCallback.onEmailError(MyApplication.getAppInstance().getString(R.string.err_valid_email))
        else if(password.isEmpty())
            loginCallback.onPasswordError(MyApplication.getAppInstance().getString(R.string.err_enter_password))
        else {
            callBack.showLoader()
//            callLoginApi(HashMap())
            fcmModel.signInWithCredential(EmailAuthProvider.getCredential(email, password), object : GenericApiCallback<String> {
                override fun onSuccess(t: String) {
                    callBack.hideLoader()
                    loginCallback.onSuccessfullyLogin()
                }

                override fun onerror(error: Throwable) {
                    callBack.hideLoader()
                    loginCallback.onErrorLogin(error.message!!)
                }
            })
        }
    }

    fun register(view: View) {
        loginCallback.onSignUp()
    }

    interface CallBackLogin {
        fun onEmailError(emailError: String)
        fun onPasswordError(passwordError: String)
        fun onSignUp()
        fun onForgotPassword()
        fun onSuccessfullyLogin()
        fun onErrorLogin(errorMassege: String)
    }
}