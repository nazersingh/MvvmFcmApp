package com.nazer.saini.fcmapp.manager.dagger.module

import com.nazer.saini.fcmapp.application.MyApplication
import dagger.Module
import javax.inject.Singleton
import dagger.Provides


@Module
class AppModule(val myapp: MyApplication) {
    private var mApplication: MyApplication = myapp
//
//    @Provides
//    @Singleton
//    fun getMyApplicationContext(): Application{
//        return mApplication
//    }

    @Provides
    @Singleton
    fun provideApp():MyApplication{return MyApplication.getAppInstance()}
}