package com.utsman.pexel.paged

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import com.utsman.core.logi
import com.utsman.pexel.Pexel
import com.utsman.pexel.api.RetrofitInstance
import com.utsman.pexel.local.PexelDb
import com.utsman.recycling.paged.extentions.NetworkState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class PexelPagedSource(private val disposable: CompositeDisposable, context: Context) : ItemKeyedDataSource<Long, Pexel>() {

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()
    private var page = 1
    private val pexelDb = PexelDb.getInstance(context)


    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Pexel>
    ) {
        networkState.postValue(NetworkState.LOADING)
        val disposePexel = RetrofitInstance.curatedRx(page)
            .doOnNext { page++ }
            .map { it.photos }
            .doOnNext {
                pexelDb.pexelDao().insert(it)
            }
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                networkState.postValue(NetworkState.LOADED)
                callback.onResult(it)
            },{
                logi("error")
                networkState.postValue(NetworkState.error(it.localizedMessage))
            })

        disposable.add(disposePexel)
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Pexel>) {
        networkState.postValue(NetworkState.LOADING)
        val disposePexel = RetrofitInstance.curatedRx(page)
            .doOnNext { page++ }
            .map { it.photos }
            .doOnNext {
                pexelDb.pexelDao().insert(it)
            }
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                networkState.postValue(NetworkState.LOADED)
                callback.onResult(it)
            },{
                logi("error")
                networkState.postValue(NetworkState.error(it.localizedMessage))
            })

        disposable.add(disposePexel)
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Pexel>) {
    }

    override fun getKey(item: Pexel): Long = item.id
}

class PexelDataFactory(private val disposable: CompositeDisposable, private val context: Context) : DataSource.Factory<Long, Pexel>() {

    val dataSource: MutableLiveData<PexelPagedSource> = MutableLiveData()
    override fun create(): DataSource<Long, Pexel> {
        val data = PexelPagedSource(disposable, context)
        dataSource.postValue(data)
        return data
    }
}