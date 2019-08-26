package com.utsman.unsplash.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.utsman.unsplash.Unsplash

@Database(entities = [Unsplash::class], version = 1)
abstract class UnsplashDb : RoomDatabase() {
    abstract fun unsplashDao(): UnsplashDao

    companion object {

        @Volatile
        private var instance: UnsplashDb? = null

        private fun buildDb(context: Context) =
            Room.databaseBuilder(context.applicationContext, UnsplashDb::class.java, "unsplash.db").build()

        fun getInstance(context: Context): UnsplashDb = instance ?: synchronized(this) {
            instance ?: buildDb(context).also { instance = it }
        }
    }
}