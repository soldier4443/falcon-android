package com.turastory.falcon.ui.feed

import com.turastory.falcon.data.source.FeedDataSource
import com.turastory.falcon.data.source.local.Feed
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by tura on 2018-10-22.
 *
 * View <-> Model 사이를 연결하는 친구 ..
 */
class FeedViewModel(private val dataSource: FeedDataSource) {

    fun getFeeds(): Single<List<Feed>> {
        return dataSource.getAllFeeds()
    }

    fun addFeed(feed: Feed): Completable {
        return dataSource.addNewFeed(feed)
    }
}