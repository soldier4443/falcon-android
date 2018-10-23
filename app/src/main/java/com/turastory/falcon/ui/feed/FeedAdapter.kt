package com.turastory.falcon.ui.feed

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.turastory.falcon.R
import com.turastory.falcon.data.source.local.Feed
import com.turastory.falcon.ui.inflate
import kotlinx.android.synthetic.main.item_feed.view.*
import org.ocpsoft.prettytime.PrettyTime

/**
 * Created by tura on 2018-09-15.
 */
class FeedAdapter(private val recyclerView: RecyclerView) : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    private val feeds = mutableListOf<Feed>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_feed))
    }

    override fun getItemCount() = feeds.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(feeds[position])
    }

    fun updateItems(items: List<Feed>) {
        feeds.clear()
        feeds.addAll(items)
        notifyDataSetChanged()
    }

    fun addNewItem(feed: Feed) {
        feeds.add(feed)
        notifyItemInserted(feeds.size)
        recyclerView.scrollToPosition(feeds.size - 1)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(feed: Feed) {
            // TODO: 이미지 보여주기
            // TODO: content 너무 길 경우 요약
            with(itemView) {
                timeText.text = PrettyTime().format(feed.created)
                authorText.text = context.getString(R.string.feed_author_template, feed.authorId)
                likeCountText.text = "${feed.like}"
                commentCountText.text = "${feed.comments}"
                contentText.text = feed.content
            }
        }
    }
}