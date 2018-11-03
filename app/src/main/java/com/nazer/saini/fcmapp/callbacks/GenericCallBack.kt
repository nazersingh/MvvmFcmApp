package com.nazer.saini.fcmapp.callbacks

interface GenericCallBack<T> {

    fun onSuccess(t:T)
    fun onerror(error:Throwable)
    fun onSubscribe()
}
