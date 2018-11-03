package com.nazer.saini.fcmapp.ui.fragment.signup.mobileverification

import android.text.TextWatcher
import android.view.View
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.nazer.saini.fcmapp.application.MyApplication
import com.nazer.saini.fcmapp.callbacks.onTextChangeCallback
import com.nazer.saini.fcmapp.ui.base.BaseObserver
import com.nazer.saini.fcmapp.ui.base.FcmModel
import com.nazer.saini.fcmapp.util.CommonUtils
import java.util.*
import javax.inject.Inject
import com.google.firebase.auth.PhoneAuthProvider
import com.nazer.saini.fcmapp.callbacks.GenericApiCallback


open class MobileVerificationViewModel(baseObserver: BaseObserver,var mobileVerication: GenericApiCallback<String>):Observable() {

    @Inject
    lateinit var fcmModel: FcmModel

    var mFirstDigit:String=""
    var mVerificationID:String=""
//    var mSecondDigit:String=""
//    var mThirdDigit:String=""
//    var mFourthDigit:String=""
//    var mFifthDigit:String=""
//    var mSixthDigit:String=""



init {
    MyApplication.getAppInstance().component.fcmModel(this)

   fcmModel.startPhoneNumberVerification("+919501005730",object:onOtpVerification{

       override fun onCodeSent(verificationId: String, token: String) {
        mVerificationID=verificationId;

       }

       override fun onOtpMessageread(otpCode: CharArray) {
           setChanged()
           mFirstDigit=otpCode.toString()
//           mSecondDigit=otpCode[1].toString()
//           mThirdDigit=otpCode[2].toString()
//           mFourthDigit=otpCode[3].toString()
//           mFifthDigit=otpCode[4].toString()
//           mSixthDigit=otpCode[5].toString()
           notifyObservers()
       }

   })
}

    fun getOtpFromEditText(): TextWatcher {
        return CommonUtils.onEditTextType(object : onTextChangeCallback {
            override fun afterTextChange(str: String) {
                mFirstDigit=str
            }
        })
    }

    interface onOtpVerification
    {
        fun onCodeSent(verificationId:String,token:String)
        fun onOtpMessageread(otp:CharArray)
    }


    fun verify(view: View)
    {

        val credential = PhoneAuthProvider.getCredential(mVerificationID, mFirstDigit)
        fcmModel.signInWithCredential(credential,mobileVerication)
    }
}