package com.utsman.unsplash

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Url(
    @ColumnInfo(name = "small")
    val small: String,
    @ColumnInfo(name = "regular")
    val regular: String
)

@Entity
data class Link(
    @ColumnInfo(name = "html")
    val html: String
)

@Entity
data class Unsplash(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val unsplashId: String,
    @Embedded
    val links: Link,
    @Embedded
    val urls: Url
)