package com.turastory.falcon.ui.feed

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.turastory.falcon.R
import com.turastory.falcon.ui.plusAssign
import com.turastory.falcon.util.EventBus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.feed_fragment.*


class FeedFragment : Fragment() {

    companion object {
        fun newInstance() = FeedFragment()
    }

    private var compositeDisposable = CompositeDisposable()

    private val feedAdapter by lazy { FeedAdapter(feedListView) }
    private var viewModel: FeedViewModel? = null

    private var subscription: Disposable? = null

    fun setViewModel(feedViewModel: FeedViewModel) {
        this.viewModel = feedViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.feed_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFeedRecyclerView()
    }

    private fun setupFeedRecyclerView() {
        feedListView.apply {
            layoutManager = LinearLayoutManager(context).apply {
                reverseLayout = true
                stackFromEnd = true
            }

            adapter = feedAdapter

            if (itemAnimator is SimpleItemAnimator)
                (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

            // TODO Incremental Pagination
//            addOnScrollListener(object : InfiniteScrollListener(10, linearLayoutManager) {
//                override fun onScrolledToEnd(firstVisibleItemPosition: Int) {
//                    feedAdapter.loadNewFeeds()
//                }
//            })
        }
    }

    override fun onResume() {
        super.onResume()

        subscription = EventBus.subject.doOnError {
            Log.e("asdf", "Error while event bus.", it)
        }.subscribe {
            Log.e("asdf", "received event - $it")
            if (it == "add_feed") {
                viewModel?.addFeed()
            }
        }

        viewModel?.let { vm ->
            compositeDisposable = CompositeDisposable()
            compositeDisposable += vm.getFeeds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Log.e("asdf", "Error while get feeds", it) }
                .subscribe { items ->
                    feedAdapter.updateItems(items)
                }

            compositeDisposable += vm.getNewFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Log.e("asdf", "Error while showing new feed", it) }
                .subscribe {
                    feedAdapter.addNewItem(it)
                }

            compositeDisposable += vm.getUpdateFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Log.e("asdf", "Error while showing new feed", it) }
                .subscribe {
                    feedAdapter.updateItem(it)
                }
        }
    }

    override fun onPause() {
        super.onPause()

        subscription?.dispose()
        compositeDisposable.dispose()
    }
}

