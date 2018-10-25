package com.turastory.falcon.data.source

import com.turastory.falcon.data.source.local.Feed
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by tura on 2018-10-22.
 *
 * Feed data source definition.
 */
interface FeedDataSource {
    fun getAllFeeds(): Single<List<Feed>>
    fun addNewFeed(feed: Feed): Completable
    fun clearAllFeeds(): Completable
    fun updateFeed(newFeed: Feed): Completable
}