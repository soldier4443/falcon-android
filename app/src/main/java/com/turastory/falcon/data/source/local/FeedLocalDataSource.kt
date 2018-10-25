package com.turastory.falcon.data.source.local

import com.turastory.falcon.data.source.FeedDataSource
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by tura on 2018-10-22.
 *
 * Local data source implementation using Room.
 */
class FeedLocalDataSource(private val feedDao: FeedDao) : FeedDataSource {

    override fun getAllFeeds(): Single<List<Feed>> {
        return Single.fromCallable { feedDao.getAll() }
    }

    override fun addNewFeed(feed: Feed): Completable {
        return Completable.fromCallable { feedDao.insert(feed) }
    }

    override fun clearAllFeeds(): Completable {
        return Completable.fromCallable { feedDao.clearAll() }
    }

    override fun updateFeed(newFeed: Feed): Completable {
        return Completable.fromCallable { feedDao.updateFeed(newFeed) }
    }
}