package com.nazer.saini.fcmapp.ui.fragment.signup.mobileverification

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nazer.saini.fcmapp.R
import com.nazer.saini.fcmapp.callbacks.GenericApiCallback
import com.nazer.saini.fcmapp.databinding.MobileNumberVarificationFragmentBinding
import com.nazer.saini.fcmapp.ui.base.BaseFragment
import com.nazer.saini.fcmapp.ui.base.BaseObserver
import com.nazer.saini.fcmapp.ui.dialogues.Dialogues
import com.nazer.saini.fcmapp.ui.fragment.home.HomeScreenFragment
import io.reactivex.disposables.Disposable
import java.util.*

class MobileVerificationFragment : BaseFragment(), BaseObserver,GenericApiCallback<String> {

    var viewModel = MobileVerificationViewModel(this,this)
    private var binding: MobileNumberVarificationFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.mobile_number_varification_fragment, container, false)
        setUpView()
        return binding?.root
    }

    fun setUpView()
    {
        binding?.verification = viewModel
    }

    override fun setUpObservers() {

    }

    override fun showLoader() {

    }

    override fun hideLoader() {
    }

    override fun addDisposal(disposable: Disposable) {
        addViewDisposal(disposable)
    }

    override fun update(o: Observable?, arg: Any?) {

    }

    override fun onerror(error: Throwable) {
        Dialogues.setMessage(error.message!!)
                .setPositiveButtonText(Dialogues.POSITIVE_BUTTON_OK)
                .isCancelAble(false)
                .showDialogueOnlyText(activity!!)
    }

    override fun onSuccess(t: String) {
        clearBackStack()
        onAddFragment(HomeScreenFragment())
    }

}