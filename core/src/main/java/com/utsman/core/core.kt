@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATION")

package com.utsman.core

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.paging.PagedList
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun logi(msg: String?) = Log.i("anjaaay", msg)
fun loge(msg: String?) = Log.e("anjaaay", msg)

fun Context.toast(msg: String?) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

fun ImageView.load(url: String?) = GlideApp.with(context)
    .load(url)
    .diskCacheStrategy(DiskCacheStrategy.ALL)
    .centerCrop()
    .placeholder(android.R.color.darker_gray)
    .into(this)

fun configPaged(size: Int): PagedList.Config = PagedList.Config.Builder()
    .setPageSize(size)
    .setEnablePlaceholders(true)
    .build()

fun getConnectivity(context: Context?) : Boolean {
    val conManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = conManager.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting == true
}

const val BASEURL_UNSPLASH = "https://api.unsplash.com/"
const val APIKEY_UNSPLASH = "9c72b38fec37970e35dbe5c8d558c2bcb42eb72ca36a038cde056bc9d536dbe9"

const val HEADER_PEXEL = "Authorization: 563492ad6f91700001000001880a2e3eb5d6452a94a3dd050f6395a6"
const val BASEURL_PEXEL = "https://api.pexels.com/"

