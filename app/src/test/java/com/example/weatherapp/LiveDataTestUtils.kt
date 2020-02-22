package com.example.weatherapp

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: (data:T) -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(5)
    val observer = Observer<T> { o ->
        data = o
        latch.countDown()
    }
    this.observeForever(observer)

    try {
        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            removeObserver(observer)
            afterObserve.invoke(data as T)
        } else{
            afterObserve.invoke(data as T)
        }

    } finally {
        this.removeObserver(observer)
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}

