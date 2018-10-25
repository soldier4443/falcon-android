package com.turastory.falcon.ui.feed

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.turastory.falcon.R
import com.turastory.falcon.ui.inflate
import kotlinx.android.synthetic.main.item_feed.view.*
import org.ocpsoft.prettytime.PrettyTime

/**
 * Created by tura on 2018-09-15.
 *
 * Think it as a dumb View. doesn't know about ViewModel, and just present the state of the feeds.
 */
class FeedAdapter(private val recyclerView: RecyclerView) : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    private val feeds = mutableListOf<FeedItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_feed))
    }

    override fun getItemCount() = feeds.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(feeds[position])
    }

    fun updateItems(items: List<FeedItem>) {
        feeds.clear()
        feeds.addAll(items)
        notifyDataSetChanged()
    }

    fun addNewItem(newFeedItem: FeedItem) {
        feeds.add(newFeedItem)
        notifyItemInserted(feeds.size)
        recyclerView.scrollToPosition(feeds.size - 1)
    }

    fun updateItem(newFeedItem: FeedItem) {
        val target = feeds.find { feedItem -> feedItem.feed.id == newFeedItem.feed.id }
        val pos = feeds.indexOf(target)
        feeds.removeAt(pos)
        feeds.add(pos, newFeedItem)
        notifyItemChanged(pos)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(feedItem: FeedItem) {
            // TODO: 이미지 보여주기
            // TODO: content 너무 길 경우 요약
            with(itemView) {
                val feed = feedItem.feed

                timeText.text = PrettyTime().format(feed.created)
                authorText.text = context.getString(R.string.feed_author_template, feed.authorId)
                likeCountText.text = "${feed.like}"
                commentCountText.text = "${feed.comments}"
                contentText.text = feed.content
                likeButton.setOnClickListener {
                    feedItem.onLikeChange(!feed.markAsLiked)
                }

                if (feed.markAsLiked) {
                    likeImage.setImageResource(R.drawable.ic_like)
                } else {
                    likeImage.setImageResource(R.drawable.ic_like_outline)
                }
            }
        }
    }
}