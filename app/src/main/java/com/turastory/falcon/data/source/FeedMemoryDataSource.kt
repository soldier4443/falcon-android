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

    override fun updateFeed(newFeed: Feed): Completable {
        return Completable.fromAction {
            feeds.find { feed -> feed.id == newFeed.id }
                ?.apply {
                    this.markAsLiked = newFeed.markAsLiked
                    this.like = newFeed.like
                    this.comments = newFeed.comments
                    this.content = newFeed.content
                }
        }
    }
}