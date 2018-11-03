package com.nazer.saini.fcmapp.ui.base

import android.support.v4.app.Fragment
import com.nazer.saini.fcmapp.callbacks.GenericCallBack
import com.nazer.saini.fcmapp.ui.activity.MainActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment:Fragment() {

    abstract fun setUpObservers()

    private var mCompositeDisposable = CompositeDisposable()

    fun setToolbarTitle(title: String) {
        showToolbar()
        (context as BaseActivity).setTitle(title)
    }

    fun setToolbarSubTitle(title: String)=(context as BaseActivity).setSubtitle(title)

    fun hideToolbar() = (context as BaseActivity).hideToolbar()

    fun showToolbar()=(context as BaseActivity).showToolbar()


    fun onAddFragment(fragment: Fragment)= (context as MainActivity).addFragment(fragment)

    fun clearBackStack()=(context as BaseActivity).clearStack()

    fun showToast(msg:String)=(context as BaseActivity).showToast(msg)

    fun popFragment() =(context as BaseActivity).popFragment()

    fun setCurrentFragmentCallBack(genricCallback: GenericCallBack<Any>)= (context as BaseActivity).setCurrentFragmentCallBack(genricCallback)

    fun getFirebaseAuthInstance()=(context as BaseActivity).getfirebaseAuthInstance()

    fun logout()=(context as BaseActivity).logoutUser()

    fun checkStatusApi(statusCode:Int,message:String)=(context as BaseActivity).checkApiSatusMessage(statusCode,message)

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        (context as BaseActivity).updateToollbarTitle(this)
    }
    open fun showApiCallLoader(){(context as BaseActivity).showApiCallLoader()}

    fun addViewDisposal(disposable: Disposable)=mCompositeDisposable.add(disposable)

    override fun onDestroyView() {
        super.onDestroyView()
        mCompositeDisposable.clear()
    }

}