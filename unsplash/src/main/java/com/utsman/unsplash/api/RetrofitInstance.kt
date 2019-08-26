package com.utsman.unsplash.api

import com.utsman.core.BASEURL_UNSPLASH
import com.utsman.unsplash.Unsplash
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInstance {

    @GET("/photos")
    fun getListPhotos(@Query("page") page: Int,
                      @Query("client_id") clientId: String) : Flowable<List<Unsplash>>

    companion object {
        private fun create(): RetrofitInstance {

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASEURL_UNSPLASH)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()

            return retrofit.create(RetrofitInstance::class.java)
        }

        fun getListPhoto(page: Int, clientId: String): Flowable<List<Unsplash>> =
            create().getListPhotos(page, clientId)
                .subscribeOn(Schedulers.io())
    }
}