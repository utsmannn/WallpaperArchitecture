package com.utsman.unsplash.paged

import android.content.Context
import android.net.NetworkInfo
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.toLiveData
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.github.pwittchen.reactivenetwork.library.rx2.ConnectivityPredicate
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.utsman.core.APIKEY_UNSPLASH
import com.utsman.core.loge
import com.utsman.core.logi
import com.utsman.recycling.paged.extentions.NetworkState
import com.utsman.unsplash.api.RetrofitInstance
import com.utsman.unsplash.Unsplash
import com.utsman.unsplash.convertInt
import com.utsman.unsplash.local.UnsplashDb
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UnsplashPagedSource(private val disposable: CompositeDisposable, private val context: Context) : ItemKeyedDataSource<String, Unsplash>() {

    private var currentPage = 1
    var networkState: MutableLiveData<NetworkState> = MutableLiveData()

    private val unsplashDb = UnsplashDb.getInstance(context)
    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<Unsplash>
    ) {
        //callback.onResult(emptyList)
        networkState.postValue(NetworkState.LOADING)
        val disposePhoto = RetrofitInstance.getListPhoto(1, APIKEY_UNSPLASH)
            .doOnNext { currentPage++ }
            .doOnNext {
                unsplashDb.unsplashDao().insert(it)
                logi("inserted")
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                networkState.postValue(NetworkState.LOADED)
                callback.onResult(it)

            }, {
                loge(it.localizedMessage)
                networkState.postValue(NetworkState.error(it.localizedMessage))
            })

        disposable.add(disposePhoto)
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<Unsplash>) {
        networkState.postValue(NetworkState.LOADING)
        val disposePhoto = RetrofitInstance.getListPhoto(currentPage, APIKEY_UNSPLASH)
            .doOnNext { currentPage++ }
            .doOnNext {
                unsplashDb.unsplashDao().insert(it)
                logi("inserted")
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                networkState.postValue(NetworkState.LOADED)
                callback.onResult(it)

            }, {
                loge(it.localizedMessage)
                networkState.postValue(NetworkState.error(it.localizedMessage))
            })

        disposable.add(disposePhoto)
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<Unsplash>) {
    }

    override fun getKey(item: Unsplash): String = item.unsplashId

}

class UnsplashDataFactory(private val disposable: CompositeDisposable, private val context: Context) : DataSource.Factory<String, Unsplash>() {

    val dataSource: MutableLiveData<UnsplashPagedSource> = MutableLiveData()
    override fun create(): DataSource<String, Unsplash> {

        val data = UnsplashPagedSource(disposable, context)

        dataSource.postValue(data)
        return data
    }
}