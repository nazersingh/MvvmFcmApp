package com.nazer.saini.fcmapp.callbacks

interface GenericApiCallback <T> {

    fun onSuccess(t:T)
    fun onerror(error:Throwable)
}
