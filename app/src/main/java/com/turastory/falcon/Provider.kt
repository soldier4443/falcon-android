package com.turastory.falcon

import android.content.Context
import com.turastory.falcon.data.source.FeedDataSource
import com.turastory.falcon.data.source.FeedDataSourceImpl
import com.turastory.falcon.data.source.FeedMemoryDataSource
import com.turastory.falcon.data.source.local.FalconRoomDatabase
import com.turastory.falcon.data.source.local.FeedLocalDataSource

/**
 * Created by tura on 2018-10-22.
 */

object Provider {
    private val memoryDataSource = FeedMemoryDataSource()

    fun provideDataSource(context: Context): FeedDataSource =
        FalconRoomDatabase.getInstance(context).let {
            return FeedDataSourceImpl(
                memoryDataSource,
                FeedLocalDataSource(it.feedDao()))
        }
}