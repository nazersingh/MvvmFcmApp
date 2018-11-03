package com.nazer.saini.fcmapp.ui.activity

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.nazer.saini.fcmapp.application.MyApplication
import com.nazer.saini.fcmapp.callbacks.GenericCallBack
import com.nazer.saini.fcmapp.pojo.Android
import com.nazer.saini.fcmapp.pojo.CharacterModel
import com.nazer.saini.fcmapp.ui.base.BaseModel
import com.nazer.saini.fcmapp.ui.base.BaseObserver
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject


class MainViewModel(var callBack: BaseObserver) : Observable() {

    @Inject
    lateinit var app: MyApplication

    @Inject
    lateinit var apiCall: BaseModel


    var androidList = MutableLiveData<List<CharacterModel>>()
    var androidResponse = MutableLiveData<Android>()
    private val TAG: String = this.javaClass.simpleName


    private var mCompositeDisposable = CompositeDisposable()

    init {
        MyApplication.getAppInstance().component.model(this)

//        app.component.model(this) //still error
    }


    fun getListData(): LiveData<List<CharacterModel>> {
        return androidList
    }


    fun loadJSON() {

        callBack.addDisposal(apiCall.loadJSON(object : GenericCallBack<Android> {
            override fun onSubscribe() {
                callBack.showLoader()
            }

            override fun onSuccess(t: Android) {
                callBack.hideLoader()
                androidResponse.value = t
                setChanged()
                notifyObservers()
            }

            override fun onerror(error: Throwable) {
                callBack.hideLoader()
            }
        }))
    }
//
//    override fun onCleared() {
//        super.onCleared()
//        Log.e(TAG, "onClear")
//        mCompositeDisposable?.clear()
//    }
}




