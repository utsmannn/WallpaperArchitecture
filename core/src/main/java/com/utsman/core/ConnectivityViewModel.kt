package com.utsman.core

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ConnectivityViewModel(application: Application) : BaseAndroidViewModel(application) {

    private val isConnected: MutableLiveData<Boolean> = MutableLiveData()

    init {
        val rxNetwork = ReactiveNetwork.observeNetworkConnectivity(application)
            .flatMapSingle { ReactiveNetwork.checkInternetConnectivity() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { connect ->
                isConnected.postValue(connect)
            }

        disposable.addAll(rxNetwork)
    }

    fun getConnectivity() = isConnected
}