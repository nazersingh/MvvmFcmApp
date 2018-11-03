package com.nazer.saini.fcmapp.ui.base

import android.util.Log
import com.nazer.saini.fcmapp.application.MyApplication
import com.nazer.saini.fcmapp.callbacks.GenericCallBack
import com.nazer.saini.fcmapp.manager.client.ApiInterface
import com.nazer.saini.fcmapp.pojo.Android
import com.nazer.saini.fcmapp.ui.dialogues.Dialogues
import com.nazer.saini.fcmapp.util.CommonUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BaseModel {

    @Inject
    lateinit var application: MyApplication
    @Inject
    lateinit var apiInterface: ApiInterface
    private var hashMap: HashMap<Any, Any>? = null


    init {
        MyApplication.getAppInstance().component.apiCall(this)
//        application.component.apiCall(this)
    }

    fun setPayload(hashMap: HashMap<Any, Any>): BaseModel {
        this.hashMap = hashMap
        return this
    }





    fun loadJSON(genericCallBack: GenericCallBack<Android>): Disposable {

        return apiInterface.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    Log.d("Subscribe ", it.toString())
                    genericCallBack.onSubscribe()
                }
                .doOnTerminate { Log.d("OnTerminate", " terminate") }
                .subscribe({ genericCallBack.onSuccess(it) }, {
                    genericCallBack.onerror(it)
                })
    }



    /**
     * call Login
     */
    fun callLoginApi(hashMap: HashMap<Any, Any>, genericCallBack: GenericCallBack<Android>): Disposable {

        return apiInterface.login(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    Log.d("Subscribe ", it.toString())
                    genericCallBack.onSubscribe()
                }
                .doOnTerminate { Log.d("OnTerminate", " terminate") }
                .subscribe({ genericCallBack.onSuccess(it) }, {
                    genericCallBack.onerror(it)
                })
    }

    /**
     * call Api
     */
    fun callFriendsApi(hashMap: HashMap<Any, Any>, genericCallBack: GenericCallBack<Android>): Disposable {

        return apiInterface.login(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    Log.d("Subscribe ", it.toString())
                    genericCallBack.onSubscribe()
                }
                .doOnTerminate { Log.d("OnTerminate", " terminate") }
                .subscribe({ genericCallBack.onSuccess(it) }, {
                    genericCallBack.onerror(it)
                })
    }



}