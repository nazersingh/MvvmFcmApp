package com.nazer.saini.fcmapp.ui.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.nazer.saini.fcmapp.R
import com.nazer.saini.fcmapp.databinding.ActivityMainBinding
import com.nazer.saini.fcmapp.ui.base.BaseActivity
import com.nazer.saini.fcmapp.ui.base.BaseObserver
import com.nazer.saini.fcmapp.ui.fragment.splash.SplashFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*

class MainActivity : BaseActivity(), BaseObserver {

    private val TAG = MainActivity::class.java.simpleName
    var binding: ActivityMainBinding? = null
    var viewModel= MainViewModel(this)


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        hideToolbar()
        setUpBinding()
        setUpObservers()


//        viewModel.getListData().observe(this, changeObserver)

//        viewModel.loadJSON()
        addFragment(SplashFragment())
    }

    override fun setUpObservers() {
        viewModel.addObserver(this)
//        viewModel.androidResponse.observe(this, changeObserver)
    }


    override fun update(o: Observable?, arg: Any?) {
        if(o is MainViewModel)
            binding?.userData=viewModel

    }

    override fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    }
    override fun addDisposal(disposable: Disposable) {
        addViewDisposal(disposable)
    }
    override fun showLoader() {
        binding?.progress?.visibility= View.VISIBLE
    }

    override fun hideLoader() {
        binding?.progress?.visibility= View.GONE
    }

    fun addFragment(fragment: Fragment)
    {
        onAddFragment(R.id.container,fragment)
    }

    /**
     * below code is also working
     */

//    private val viewModel: MainViewModel by lazy {
//        ViewModelProviders.of(this).get(MainViewModel::class.java)
//    }

//    private val changeObserver =
////            Observer<List<CharacterModel>>{
////                Log.e(TAG," Observer called "+it?.toString()+" "+it?.size)
////            }
//            Observer<Android> {
//                Log.e(TAG, " Observer called " + it?.toString() + " " + it?.count)
////        binding?.setVariable(BR.userData, it)
//                binding?.userData = viewModel
//                binding?.executePendingBindings()
//            }


    //    private fun loadJSON() {
//
//        mCompositeDisposable?.add(apiInterface.getData()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(this::handleResponse, this::handleError))
//
//    }

//
//    private fun handleResponse(androidList: List<Android>) {
//
//        mAndroidArrayList = ArrayList(androidList)
//    Log.e(TAG," array size "+androidList.size)
////        mAdapter = DataAdapter(mAndroidArrayList!!, this)
////
////        rv_android_list.adapter = mAdapter
//    }
//
//    private fun handleError(error: Throwable) {
//
//        Log.d(TAG, error.localizedMessage)
//
//        Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
//    }

}

