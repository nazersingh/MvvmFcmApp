package com.nazer.saini.fcmapp.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nazer.saini.fcmapp.R
import com.nazer.saini.fcmapp.ui.base.BaseFragment
import com.nazer.saini.fcmapp.ui.base.BaseObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*

class HomeScreenFragment:BaseFragment(), BaseObserver,HomeScreenFragmentViewModel.HomeScreenCallBack{

    private var homeViewmodel=HomeScreenFragmentViewModel(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_home_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showToolbar()
        setUpObservers()
        checkStatusApi(1000," Session expred.")
    }

    override fun setUpObservers() {


    }

    override fun addDisposal(disposable: Disposable) {
        addViewDisposal(disposable)
    }

    override fun update(o: Observable?, arg: Any?) {

    }

    override fun onFriendsclick() {

    }

    override fun onMapClick() {
    }

    override fun onInviteClick() {
    }


}