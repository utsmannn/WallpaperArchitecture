package com.utsman.unsplash.local

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.utsman.unsplash.Unsplash

@Dao
interface UnsplashDao {

    @Query("select * from Unsplash")
    fun getAllLocal(): DataSource.Factory<Int, Unsplash>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(photos: List<Unsplash>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(unsplash: Unsplash)

    @Query("select * from Unsplash")
    fun getAllLocalAsList(): List<Unsplash>

    @Query("select * from Unsplash where id >= :id limit :size")
    fun getAllLocalAsList(id: Int, size: Int): List<Unsplash>
}