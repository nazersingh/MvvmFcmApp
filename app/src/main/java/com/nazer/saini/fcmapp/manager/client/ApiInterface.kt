package com.nazer.saini.fcmapp.manager.client

import com.nazer.saini.fcmapp.pojo.Android
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiInterface {

    @GET("people/")
    fun getData() : Observable<Android>

    @POST("people/")
    fun login(@Body hashMap: HashMap<Any,Any>) : Observable<Android>
}