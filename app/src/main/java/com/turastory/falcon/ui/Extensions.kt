package com.turastory.falcon.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by tura on 2018-10-22.
 */

fun ViewGroup.inflate(layoutId: Int): View =
    LayoutInflater.from(this.context).inflate(layoutId, this, false)

fun View.makeGone() {
    this.visibility = View.GONE
}

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeInvisible() {
    this.visibility = View.INVISIBLE
}

operator fun CompositeDisposable.plusAssign(other: Disposable) {
    this.add(other)
}

fun <E> List<E>.random(): E {
    return get(ThreadLocalRandom.current().nextInt(size))
}

fun IntRange.random(): Int {
    return ThreadLocalRandom.current().nextInt(endInclusive - start + 1) + start
}

fun Int.random() = (0..this).random()