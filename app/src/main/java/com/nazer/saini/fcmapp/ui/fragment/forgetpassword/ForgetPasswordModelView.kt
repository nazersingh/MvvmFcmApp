package com.nazer.saini.fcmapp.ui.fragment.forgetpassword

import android.view.View
import com.nazer.saini.fcmapp.application.MyApplication
import com.nazer.saini.fcmapp.callbacks.GenericApiCallback
import com.nazer.saini.fcmapp.callbacks.GenericCallBack
import com.nazer.saini.fcmapp.pojo.Android
import com.nazer.saini.fcmapp.ui.base.BaseModel
import com.nazer.saini.fcmapp.ui.base.BaseObserver
import com.nazer.saini.fcmapp.ui.base.FcmModel
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class ForgetPasswordModelView(var callBack: BaseObserver, var forgotCallBack: GenericApiCallback<String>)  : Observable() {

    @Inject
    lateinit var baseModel: BaseModel
    @Inject
    lateinit var fcmModel: FcmModel

    private val TAG: String = this.javaClass.simpleName

    init {
        MyApplication.getAppInstance().component.forgot(this)
    }



    fun callForgotApi(hashMap: HashMap<Any, Any>) {
        callBack.addDisposal(baseModel.callLoginApi(hashMap, object : GenericCallBack<Android> {
            override fun onSubscribe() {
                callBack.showLoader()
            }

            override fun onSuccess(t: Android) {
                callBack.hideLoader()

                // when using observable
                forgotCallBack.onSuccess(t.imageUrl)
                setChanged()
                notifyObservers()
            }

            override fun onerror(error: Throwable) {
                callBack.hideLoader()
                forgotCallBack.onerror(error)
            }
        }))
    }

    fun callForgot(view: View)
    {
        callForgotApi(HashMap())
    }

}