package com.utsman.unsplash.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.utsman.core.BaseAndroidViewModel
import com.utsman.recycling.paged.extentions.NetworkState
import com.utsman.unsplash.Unsplash
import com.utsman.unsplash.local.UnsplashDb
import com.utsman.unsplash.paged.UnsplashDataFactory

class UnsplashViewModel(application: Application) : BaseAndroidViewModel(application) {

    private var dataFactory: UnsplashDataFactory? = null
    private val db = UnsplashDb.getInstance(application)
    private var networkState: LiveData<NetworkState> = MutableLiveData()

    init {
        dataFactory = UnsplashDataFactory(disposable, application)

        networkState = Transformations.switchMap(dataFactory?.dataSource!!) {
            it.networkState
        }
    }

    private fun getConnectivity(context: Context?) : Boolean {
        val conManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = conManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    private fun configPaged(size: Int): PagedList.Config = PagedList.Config.Builder()
        .setPageSize(size)
        .setEnablePlaceholders(true)
        .build()

    fun getPhoto(): LiveData<PagedList<Unsplash>> {

        return when(getConnectivity(getApplication())) {
            true -> LivePagedListBuilder(dataFactory!!, configPaged(4)).build()
            false -> db.unsplashDao().getAllLocal().toLiveData(4)
        }

        //return db.unsplashDao().getAllLocal().toLiveData(4)
    }

    fun getLoader(): LiveData<NetworkState>? = networkState
}