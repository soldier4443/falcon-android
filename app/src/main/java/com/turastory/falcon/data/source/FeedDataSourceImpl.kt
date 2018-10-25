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
class FeedDataSourceImpl(private val memoryDataSource: FeedMemoryDataSource,
                         private val localDataSource: FeedLocalDataSource) : FeedDataSource {
    override fun getAllFeeds(): Single<List<Feed>> {
        return localDataSource.getAllFeeds()
    }

    override fun addNewFeed(feed: Feed): Completable {
        return localDataSource.addNewFeed(feed)
    }

    override fun clearAllFeeds(): Completable {
        return localDataSource.clearAllFeeds()
    }

    override fun updateFeed(newFeed: Feed): Completable {
        return localDataSource.updateFeed(newFeed)
    }
}