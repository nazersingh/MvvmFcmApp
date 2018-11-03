package com.nazer.saini.fcmapp.manager.dagger.module

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.OkHttpClient
import android.util.Log
import okhttp3.Cache
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import android.app.Application
import com.nazer.saini.fcmapp.manager.client.ApiInterface
import com.nazer.saini.fcmapp.ui.base.BaseModel
import com.nazer.saini.fcmapp.ui.base.FcmModel
import com.nazer.saini.fcmapp.util.AppConstants
import javax.inject.Named


@Module
class NetModule {

    @Provides
    @Singleton
    fun getApiInterface(): ApiInterface {
        return getRequestInterface(AppConstants.BASE_URL)
    }

    @Provides
    @Singleton
    fun getFcmModel(): FcmModel {
        return FcmModel()
    }

    @Named("thirdPartyBase")
    @Provides
    @Singleton
    fun getApiInterfaceThird(): ApiInterface {
        return getRequestInterface(AppConstants.BASE_URL)
    }

    fun getRequestInterface(base: String): ApiInterface {
        return Retrofit.Builder()
                .baseUrl(base)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkhttpClient())
                .build().create(ApiInterface::class.java)
    }


    @Provides
    @Singleton
    fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }


    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
//        client.cache(provideHttpCache())
        client.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        client.addInterceptor(Interceptor { chain ->
            val original = chain.request()
            val token = "Token"

            let { token.trim { it <= ' ' }.length > 0

                Log.e("NetModule-login token--", "--$token")
                val request = original.newBuilder()
                        .header("x-logintoken", token)
                        .method(original.method(), original.body())
                        .build()
                return@Interceptor chain.proceed(request)}
            chain.proceed(original)
        })
        return client.build()
    }

}