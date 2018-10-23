package com.turastory.falcon.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context

/**
 * Created by tura on 2018-10-22.
 */
@Database(entities = [Feed::class], version = 1)
@TypeConverters(value = [Converters::class])
abstract class FalconRoomDatabase : RoomDatabase() {
    abstract fun feedDao(): FeedDao

    companion object {
        private var INSTANCE: FalconRoomDatabase? = null

        fun getInstance(context: Context): FalconRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(FalconRoomDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FalconRoomDatabase::class.java, "falcon.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}