package com.turastory.falcon.ui.feed

import android.annotation.SuppressLint
import android.util.Log
import com.turastory.falcon.data.source.FeedDataSource
import com.turastory.falcon.data.source.local.Feed
import com.turastory.falcon.ui.random
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.*

/**
 * Created by tura on 2018-10-22.
 *
 * View <-> Model 사이를 연결하는 친구 ..
 */
class FeedViewModel(private val dataSource: FeedDataSource) {

    companion object {
        private var counter = 0L
    }

    private val newFeed: PublishSubject<FeedItem> = PublishSubject.create()
    private val updateFeed: PublishSubject<FeedItem> = PublishSubject.create()

    fun getFeeds(): Single<List<FeedItem>> {
        return dataSource.getAllFeeds().map { feeds ->
            feeds.map(this::makeFeedItem)
        }
    }

    fun getNewFeed(): Observable<FeedItem> {
        return newFeed.hide()
    }

    fun getUpdateFeed(): Observable<FeedItem> {
        return updateFeed.hide()
    }

    // Construct FeedItem from given Feed
    private fun makeFeedItem(feed: Feed): FeedItem {
        return FeedItem(feed) { isLike -> onLike(feed, isLike) }
    }

    @SuppressLint("CheckResult")
    private fun onLike(feed: Feed, like: Boolean) {
        if (like) {
            feed.like++
            feed.markAsLiked = true
        } else {
            feed.like--
            feed.markAsLiked = false
        }

        dataSource.updateFeed(feed)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .doOnError { Log.e("asdf", "Error occurred while updating feed state", it) }
            .subscribe {
                updateFeed.onNext(makeFeedItem(feed))
            }
    }

    @SuppressLint("CheckResult")
    fun addFeed() {
        // TODO: Create Feed from user inputs.
        val feed = Feed(counter++,
            "tura", Date(Date().time - (3600 * 24 * 7).random() * 1000),
            9.random(),
            99.random())

        // Should I get disposable and handle the subscription here?
        // My thoughts: No, because once the fragment is destroyed, then
        // subscription to "newFeed" will also be disposed, so it's okay.
        // But what if this ViewModel is destroyed? Let's test it out.
        // TODO: Test whether should I manage this or not.
        dataSource.addNewFeed(feed)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .doOnError { Log.e("asdf", "Error occurred while adding new feed", it) }
            .subscribe {
                newFeed.onNext(makeFeedItem(feed))
            }
    }
}