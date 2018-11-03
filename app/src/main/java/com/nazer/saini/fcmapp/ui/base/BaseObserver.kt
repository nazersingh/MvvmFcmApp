package com.nazer.saini.fcmapp.ui.base

import com.nazer.saini.fcmapp.application.MyApplication
import com.nazer.saini.fcmapp.ui.dialogues.Dialogues
import io.reactivex.disposables.Disposable
import java.util.*

interface BaseObserver:Observer {

    fun showLoader()
    {
        Dialogues.showLoadingDialog(MyApplication.getAppInstance().applicationContext)
    }
    fun hideLoader()
    {
        Dialogues.hideLoadingDialog()
    }
    fun addDisposal(disposable: Disposable)
}