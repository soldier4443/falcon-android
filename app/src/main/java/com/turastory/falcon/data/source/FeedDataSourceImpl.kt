package com.turastory.falcon.data.source

import com.turastory.falcon.data.source.local.Feed
import com.turastory.falcon.data.source.local.FeedLocalDataSource
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Simple DataSource.
 *
 * TODO: Add remote data source, cache.
 */
class FeedDataSourceImpl(private val memoryDataSource: FeedMemoryDataSource, localDataSource: FeedLocalDataSource) : FeedDataSource {

    override fun getAllFeeds(): Single<List<Feed>> {
        return memoryDataSource.getAllFeeds()
    }

    override fun addNewFeed(feed: Feed): Completable {
        return memoryDataSource.addNewFeed(feed)
    }

    override fun clearAllFeeds(): Completable {
        return memoryDataSource.clearAllFeeds()
    }
}