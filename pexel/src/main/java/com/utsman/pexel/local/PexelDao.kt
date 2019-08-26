package com.utsman.pexel.local

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.utsman.pexel.Pexel

@Dao
interface PexelDao {

    @Query("select * from Pexel")
    fun getAllLocal(): DataSource.Factory<Int, Pexel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(photos: List<Pexel>)
}