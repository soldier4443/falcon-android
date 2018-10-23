package com.turastory.falcon

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.turastory.falcon.ui.feed.FeedFragment
import com.turastory.falcon.ui.makeGone
import com.turastory.falcon.ui.makeVisible
import com.turastory.falcon.ui.wallet.WalletFragment
import com.turastory.falcon.util.EventBus
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    // TODO: more concrete implementation
    private var state: String = "initial"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setSupportActionBar(bottomAppBar)
        addFeedButton.setOnClickListener {
            Log.e("asdf", "Sending events")
            EventBus.sendEvent("add_feed")
        }

        if (savedInstanceState == null) {
            showFeed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_app_bar_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return item?.run {
            when (itemId) {
                R.id.action_feed -> showFeed()
                R.id.action_wallet -> showWallet()
                R.id.action_settings -> toast("Settings!")
            }

            return true
        } ?: false
    }

    private fun toast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun showWallet() {
        if (state == "wallet")
            return

        state = "wallet"

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, WalletFragment.newInstance(), "wallet")
            .commitNow()

        addFeedButton.makeGone()
    }

    private fun showFeed() {
        if (state == "feed")
            return

        state = "feed"

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, FeedFragment.newInstance(), "feed")
            .commitNow()

        addFeedButton.makeVisible()
    }
}
