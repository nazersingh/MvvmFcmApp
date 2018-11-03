package com.nazer.saini.fcmapp.ui.fragment.forgetpassword

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nazer.saini.fcmapp.R
import com.nazer.saini.fcmapp.callbacks.GenericApiCallback
import com.nazer.saini.fcmapp.databinding.FragmentForgotBinding
import com.nazer.saini.fcmapp.ui.base.BaseFragment
import com.nazer.saini.fcmapp.ui.base.BaseObserver
import io.reactivex.disposables.Disposable
import java.util.*

class ForgetPasswordFragment : BaseFragment(), BaseObserver,GenericApiCallback<String>{

    private var binding :FragmentForgotBinding?= null
    var fragmentForgetPasswordModelView=ForgetPasswordModelView(this,this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot, container, false)
        setDataonView()
        setUpObservers()
        return binding?.root
    }

    fun setDataonView()
    {
        binding?.forgot=fragmentForgetPasswordModelView
    }

    override fun setUpObservers() {
        fragmentForgetPasswordModelView.addObserver(this)
    }

    override fun addDisposal(disposable: Disposable) {
    }

    override fun update(o: Observable?, arg: Any?) {
    }

    override fun onSuccess(t: String) {
    }

    override fun onerror(error: Throwable) {
    }

}