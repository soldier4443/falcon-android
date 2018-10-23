package com.turastory.falcon.ui.wallet

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.turastory.falcon.R
import kotlinx.android.synthetic.main.wallet_fragment.*

/**
 * Created by tura on 2018-09-15.
 */
class WalletFragment : Fragment() {
    companion object {
        fun newInstance() = WalletFragment()

        const val address = "H2UevVaqYWo3Quw9oKPqU632VwP9oCcKB"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.wallet_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addressValue.text = address
        hyconValue.text = "100 HYC"
    }
}