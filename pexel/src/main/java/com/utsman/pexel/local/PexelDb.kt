package com.utsman.pexel.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.utsman.pexel.Pexel

@Database(entities = [Pexel::class], version = 1)
abstract class PexelDb : RoomDatabase() {
    abstract fun pexelDao(): PexelDao

    companion object {

        @Volatile
        private var instance: PexelDb? = null

        private fun buildDb(context: Context) =
            Room.databaseBuilder(context.applicationContext, PexelDb::class.java, "pexel.db").build()

        fun getInstance(context: Context): PexelDb = instance ?: synchronized(this) {
            instance ?: buildDb(context).also { instance = it }
        }
    }

}