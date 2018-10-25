package com.turastory.falcon.ui.feed

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.turastory.falcon.Provider
import com.turastory.falcon.R
import com.turastory.falcon.data.source.FeedDataSource
import com.turastory.falcon.data.source.local.Feed
import com.turastory.falcon.ui.plusAssign
import com.turastory.falcon.ui.random
import com.turastory.falcon.util.EventBus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.feed_fragment.*
import java.util.*


class FeedFragment : Fragment() {
    
    companion object {
        fun newInstance() = FeedFragment()
        var counter: Long = 0
    }
    
    private val compositeDisposable = CompositeDisposable()
    
    private val feedAdapter by lazy { FeedAdapter(feedListView) }
    private var viewModel: FeedViewModel? = null
    
    private var subscription: Disposable? = null
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.feed_fragment, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.apply {
            Provider.provideDataSource(this)?.also { dataSource ->
                initializeViewModel(dataSource)
            }
        }
    }
    
    override fun onStart() {
        super.onStart()
        
        subscription = EventBus.subject.doOnError {
            Log.e("asdf", "Error while event bus.")
        }.subscribe {
            Log.e("asdf", "received event - $it")
            if (it == "add_feed") {
                addNewFeed()
            }
        }
    }
    
    override fun onStop() {
        super.onStop()
        
        subscription?.dispose()
        compositeDisposable.dispose()
    }
    
    private fun initializeViewModel(dataSource: FeedDataSource) {
        viewModel = FeedViewModel(dataSource)
        viewModel?.let { vm ->
            feedListView.apply {
                layoutManager = LinearLayoutManager(context).apply {
                    reverseLayout = true
                    stackFromEnd = true
                }
    
                adapter = feedAdapter
    
                if (itemAnimator is SimpleItemAnimator)
                    (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
                
                loadFeeds()
    
                // TODO Incremental Pagination
//                addOnScrollListener(object : InfiniteScrollListener(10, linearLayoutManager) {
//                    override fun onScrolledToEnd(firstVisibleItemPosition: Int) {
//                        feedAdapter.loadNewFeeds()
//                    }
//                })
            }
        }
    }
    
    private fun loadFeeds() {
        viewModel?.let { vm ->
            compositeDisposable += vm.getFeeds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    Log.e("asdf", "Error - $it")
                }
                .subscribe { items ->
                    feedAdapter.updateItems(items)
                }
        }
    }
    
    private fun addNewFeed() {
        Feed(counter,
            "tura", Date(Date().time - (3600 * 24 * 7).random() * 1000),
            9.random(),
            99.random(),
            randomStrings.random(),
            false).let { feed ->
            viewModel?.let { vm ->
                compositeDisposable += vm.addFeed(feed)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Log.e("asdf", "Error - $it")
                    }
                    .subscribe {
                        Log.e("asdf", "Success!")
                        feedAdapter.addNewItem(feed)
                    }
            }
        }
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

