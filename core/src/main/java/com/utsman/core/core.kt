@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.utsman.core

import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
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

val BASEURL_UNSPLASH = "https://api.unsplash.com/"
const val APIKEY_UNSPLASH = "9c72b38fec37970e35dbe5c8d558c2bcb42eb72ca36a038cde056bc9d536dbe9"

