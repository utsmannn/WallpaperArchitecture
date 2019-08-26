package com.utsman.pexel.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.utsman.core.BaseAndroidViewModel
import com.utsman.core.configPaged
import com.utsman.core.getConnectivity
import com.utsman.pexel.Pexel
import com.utsman.pexel.local.PexelDb
import com.utsman.pexel.paged.PexelDataFactory
import com.utsman.recycling.paged.extentions.NetworkState

class PexelViewModel(application: Application) : BaseAndroidViewModel(application) {

    private var dataFactory: PexelDataFactory? = null
    private var networkState: LiveData<NetworkState> = MutableLiveData()
    private val pexelDb = PexelDb.getInstance(application)

    init {
        dataFactory = PexelDataFactory(disposable, application)
        networkState = Transformations.switchMap(dataFactory?.dataSource!!) {
            it.networkState
        }
    }

    fun getPhoto(): LiveData<PagedList<Pexel>> {

        return when(getConnectivity(getApplication())) {
            true -> LivePagedListBuilder(dataFactory!!, configPaged(4)).build()
            false -> pexelDb.pexelDao().getAllLocal().toLiveData(4)
        }
    }

    fun getLoader() = networkState

}