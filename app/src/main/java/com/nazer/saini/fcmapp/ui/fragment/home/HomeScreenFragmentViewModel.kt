package com.nazer.saini.fcmapp.ui.fragment.home

import com.nazer.saini.fcmapp.application.MyApplication
import com.nazer.saini.fcmapp.callbacks.GenericCallBack
import com.nazer.saini.fcmapp.pojo.Android
import com.nazer.saini.fcmapp.ui.base.BaseModel
import com.nazer.saini.fcmapp.ui.base.BaseObserver
import java.util.*
import javax.inject.Inject

class HomeScreenFragmentViewModel(var callBack: BaseObserver) : Observable() {

    @Inject
    lateinit var app: MyApplication

    @Inject
    lateinit var apiCall: BaseModel

    init {
        MyApplication.getAppInstance().component.home(this)
    }
    fun loadJSON() {

        callBack.addDisposal(apiCall.loadJSON(object : GenericCallBack<Android> {
            override fun onSubscribe() {
                callBack.showLoader()
            }

            override fun onSuccess(t: Android) {
                callBack.hideLoader()
                setChanged()
                notifyObservers()
            }

            override fun onerror(error: Throwable) {
                callBack.hideLoader()
            }
        }))
    }

    interface HomeScreenCallBack {
        fun onFriendsclick()
        fun onMapClick()
        fun onInviteClick()
    }

}