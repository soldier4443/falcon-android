package com.turastory.falcon.data.source

import android.util.Log
import com.turastory.falcon.data.source.local.Feed
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by tura on 2018-10-22.
 */
class FeedMemoryDataSource : FeedDataSource {
    private val feeds = mutableListOf<Feed>()

    override fun getAllFeeds(): Single<List<Feed>> {
        Log.e("asdf", "    get all feeds - feed size: ${feeds.size}")
        return Single.just(feeds)
    }

    override fun addNewFeed(feed: Feed): Completable {
        return Completable.fromAction {
            feeds.add(feed)
            Log.e("asdf", "    run new feeds - feed size: ${feeds.size}")
        }
    }

    override fun clearAllFeeds(): Completable {
        return Completable.fromAction {
            feeds.clear()
        }
    }
}