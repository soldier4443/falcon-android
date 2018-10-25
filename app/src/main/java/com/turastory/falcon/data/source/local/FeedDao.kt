package com.turastory.falcon.data.source.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

/**
 * Created by tura on 2018-10-22.
 */

@Dao
interface FeedDao {

    @Query("SELECT * FROM feed_data ")
    fun getAll(): List<Feed>

    @Insert(onConflict = REPLACE)
    fun insert(feed: Feed)

    @Query("DELETE FROM feed_data")
    fun clearAll()

    @Update(onConflict = REPLACE)
    fun updateFeed(newFeed: Feed)
}