package com.nazer.saini.fcmapp.manager.dagger

import com.nazer.saini.fcmapp.application.MyApplication
import com.nazer.saini.fcmapp.manager.dagger.module.AppModule
import com.nazer.saini.fcmapp.manager.dagger.module.NetModule
import com.nazer.saini.fcmapp.ui.activity.MainActivity
import com.nazer.saini.fcmapp.ui.activity.MainViewModel
import com.nazer.saini.fcmapp.ui.base.BaseModel
import com.nazer.saini.fcmapp.ui.fragment.forgetpassword.ForgetPasswordModelView
import com.nazer.saini.fcmapp.ui.fragment.home.HomeScreenFragmentViewModel
import com.nazer.saini.fcmapp.ui.fragment.login.LoginViewModel
import com.nazer.saini.fcmapp.ui.fragment.signup.email.SignUpModelView
import com.nazer.saini.fcmapp.ui.fragment.signup.mobileverification.MobileVerificationViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetModule::class))
interface ApplicationComponents {


    fun poke(mainActivity: MainActivity)
    fun inject(myApplication: MyApplication)
    fun model(mainViewModel: MainViewModel)
    fun apiCall(baseModel: BaseModel)
    fun login(loginViewModel: LoginViewModel)
    fun home(homeScreenFragmentViewModel: HomeScreenFragmentViewModel)
    fun fcmModel(mobileVerificationViewModel: MobileVerificationViewModel)
    fun signup(signUpModelView: SignUpModelView)
    fun forgot(forgetPasswordModelView: ForgetPasswordModelView)

}