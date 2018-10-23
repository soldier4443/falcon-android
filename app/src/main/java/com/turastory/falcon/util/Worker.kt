package com.turastory.falcon.util

import java.util.concurrent.Executors

/**
 * Created by tura on 2018-10-22.
 */

private val executor = Executors.newFixedThreadPool(3)

fun runOnBackground(job: Runnable) {
    executor.execute(job)
}

fun stopAll() {
    executor.shutdownNow()
}