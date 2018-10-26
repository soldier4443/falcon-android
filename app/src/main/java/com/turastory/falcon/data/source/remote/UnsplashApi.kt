package com.turastory.falcon.data.source.remote

import com.turastory.falcon.BuildConfig
import com.turastory.falcon.data.source.rqrs.UnsplashResponse
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

/**
 * Created by tura on 2018-10-26.
 */

val unsplashApi: UnsplashApi = Retrofit.Builder()
    .baseUrl("https://api.unsplash.com/")
    .client(buildOkHttpClient())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(UnsplashApi::class.java)

private fun buildOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().run {
        readTimeout(10, TimeUnit.SECONDS)
        connectTimeout(5, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            // show full body
            addInterceptor(HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        addInterceptor(attachHeaders())

        build()
    }
}

private fun attachHeaders(): (Interceptor.Chain) -> Response = { chain ->
    chain.request().newBuilder()
        .addHeader("Accept-Version", "v1")
        .addHeader("Authorization", "Client-ID ${BuildConfig.UNSPLASH_ACCESS_KEY}")
        .build().run {
            chain.proceed(this)
        }
}

interface UnsplashApi {
    @GET("photos/random?featured=true")
    fun randomPhoto(): Single<UnsplashResponse>
}