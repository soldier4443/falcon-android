package com.turastory.falcon.util

import io.reactivex.subjects.PublishSubject

/**
 * Created by tura on 2018-10-23.
 *
 * TODO: 발전해야함..
 */
object EventBus {
    val subject = PublishSubject.create<String>()

    fun sendEvent(string: String) {
        subject.onNext(string)
    }
}