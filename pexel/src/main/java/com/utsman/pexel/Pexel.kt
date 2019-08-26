package com.utsman.pexel

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Pexel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "url")
    val url: String,
    @Embedded
    val src: Source) : Parcelable

@Parcelize
data class Source(
    @ColumnInfo(name = "url_medium")
    val medium: String,
    @ColumnInfo(name = "url_small")
    val small: String) : Parcelable

data class Responses(val page: Int,
                     val per_page: Int,
                     val photos: List<Pexel>)

data class Category(val query: String,
                    val url: String)