package com.turastory.falcon.data.source

import android.util.Log
import com.turastory.falcon.data.source.local.Feed
import com.turastory.falcon.data.source.local.FeedLocalDataSource
import com.turastory.falcon.data.source.remote.unsplashApi
import com.turastory.falcon.ui.random
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.*

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
        val insertImage = Random().nextFloat()

        return Completable.fromCallable {
            // Insert image for 60% of feeds.
            if (insertImage < 0.6f) {
                unsplashApi.randomPhoto()
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.computation())
                    .doOnError { Log.e("asdf", "Error while getting image.") }
                    .blockingGet().also {
                        feed.content = it.description ?: "No description."
                        feed.imageUrl = it.urls["small"].toString()
                    }
            } else {
                feed.content = randomStrings.random()
                feed.imageUrl = ""
            }
        }.andThen(localDataSource.addNewFeed(feed))
    }

    override fun clearAllFeeds(): Completable {
        return localDataSource.clearAllFeeds()
    }

    override fun updateFeed(newFeed: Feed): Completable {
        return localDataSource.updateFeed(newFeed)
    }

    private val randomStrings: List<String> = mutableListOf(
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        "Nullam id metus ac lectus auctor lobortis.",
        "Sed viverra nisl nec tellus hendrerit condimentum.",
        "Ut pellentesque est id tempor porttitor.",
        "Integer lacinia mi sit amet lacus aliquam tincidunt.",
        "Nullam vel leo cursus, dictum ipsum in, vehicula eros.",
        "Nunc volutpat felis ornare, auctor quam non, sollicitudin nisi.",
        "Curabitur sit amet lectus non mi sagittis placerat at et lectus.",
        "Phasellus molestie odio ornare ullamcorper placerat.",
        "Etiam a nibh in tortor aliquet ultricies sed et urna.",
        "In et massa sed erat fringilla suscipit.",
        "Aliquam eu tortor vel diam tempor placerat.",
        "Morbi mattis tellus vel urna lacinia sollicitudin.",
        "Vivamus eget nibh vestibulum, pulvinar mauris at, sagittis tellus.",
        "Praesent molestie magna id neque condimentum accumsan.",
        "Nullam vitae dolor non dui lacinia varius.",
        "Nulla dictum orci eget fringilla fringilla.")
}