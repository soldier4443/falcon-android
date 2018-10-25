package com.turastory.falcon.ui.feed

import com.turastory.falcon.data.source.local.Feed

/**
 * Provided to adapter that display feeds.
 */
class FeedItem(val feed: Feed,
               val onLikeChange: (Boolean) -> Unit)