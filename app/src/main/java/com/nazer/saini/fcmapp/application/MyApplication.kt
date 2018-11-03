package com.nazer.saini.fcmapp.application
import android.app.Application
import android.os.Handler
import com.crashlytics.android.BuildConfig
import com.google.firebase.FirebaseApp
import com.nazer.saini.fcmapp.manager.dagger.ApplicationComponents
import com.nazer.saini.fcmapp.manager.dagger.DaggerApplicationComponents
import com.nazer.saini.fcmapp.manager.dagger.module.AppModule
import com.nazer.saini.fcmapp.manager.dagger.module.NetModule
import io.fabric.sdk.android.Fabric
import com.crashlytics.android.Crashlytics;
import com.nazer.saini.fcmapp.util.PrintLog


class MyApplication : Application() {

    var Applicationcomponents: ApplicationComponents?=null


//    override fun onCreate() {
//        super.onCreate()
//
//        instance=this
//        Applicationcomponents = DaggerApplicationComponents.builder()
//                .appModule(AppModule(this))
//                .netModule(NetModule())
//                .build()
//    }
//
//        fun getApplicationComponent():ApplicationComponents?
//        {
//            return Applicationcomponents
//        }

    companion object {

        lateinit var instance:MyApplication
        fun getAppInstance(): MyApplication {
            return instance
        }
    }

    val component: ApplicationComponents by lazy {
        DaggerApplicationComponents.builder()
                .appModule(AppModule(instance))
                .netModule(NetModule())
                .build()
    }

    override fun onCreate() {
        super.onCreate()


        Fabric.with(this, Crashlytics())
        instance=this
        component.inject(this@MyApplication)
     }

}