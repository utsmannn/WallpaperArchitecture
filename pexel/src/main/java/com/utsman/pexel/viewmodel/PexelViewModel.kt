package com.utsman.pexel.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.utsman.core.BaseAndroidViewModel
import com.utsman.core.configPaged
import com.utsman.pexel.Pexel
import com.utsman.pexel.paged.PexelDataFactory
import com.utsman.recycling.paged.extentions.NetworkState

// private var dataFactory: UnsplashDataFactory? = null
//    private val db = UnsplashDb.getInstance(application)
//    private var networkState: LiveData<NetworkState> = MutableLiveData()
//
//    init {
//        dataFactory = UnsplashDataFactory(disposable, application)
//
//        networkState = Transformations.switchMap(dataFactory?.dataSource!!) {
//            it.networkState
//        }
//    }
//
//    private fun getConnectivity(context: Context?) : Boolean {
//        val conManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val activeNetwork = conManager.activeNetworkInfo
//        return activeNetwork?.isConnectedOrConnecting == true
//    }
//
//    fun getPhoto(): LiveData<PagedList<Unsplash>> {
//
//        return when(getConnectivity(getApplication())) {
//            true -> LivePagedListBuilder(dataFactory!!, configPaged(4)).build()
//            false -> db.unsplashDao().getAllLocal().toLiveData(4)
//        }
//    }
//
//    fun getLoader(): LiveData<NetworkState>? = networkState

class PexelViewModel(application: Application) : BaseAndroidViewModel(application) {

    private var dataFactory: PexelDataFactory? = null
    private var networkState: LiveData<NetworkState> = MutableLiveData()

    init {
        dataFactory = PexelDataFactory(disposable, application)
        networkState = Transformations.switchMap(dataFactory?.dataSource!!) {
            it.networkState
        }
    }

    fun getPhoto(): LiveData<PagedList<Pexel>> {
        return LivePagedListBuilder(dataFactory!!, configPaged(4)).build()
    }

    fun getLoader() = networkState

}