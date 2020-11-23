package com.example.exampleapp.data.api

import android.util.Log
import com.example.exampleapp.BuildConfig
import com.example.exampleapp.data.api.models.Dataset
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

interface ExampleApi {

    @GET("dataset")
    fun getDatasets(
        @Query("access_token") accessToken: String
    ): Single<List<Dataset>>

    companion object {
        private const val OKHTTP_TIMEOUT_CONNECT = 20_000L
        private const val OKHTTP_TIMEOUT_READ = 20_000L
        private const val OKHTTP_TIMEOUT_WRITE = 20_000L
        val DATE_TIME_FORMAT = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US).also { it.timeZone = TimeZone.getTimeZone("GMT") }

        @Volatile
        private var instance: ExampleApi? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: create().also { instance = it }
        }

        private fun create(): ExampleApi {
            val client = OkHttpClient.Builder()
                    .connectTimeout(OKHTTP_TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)
                    .readTimeout(OKHTTP_TIMEOUT_READ, TimeUnit.MILLISECONDS)
                    .writeTimeout(OKHTTP_TIMEOUT_WRITE, TimeUnit.MILLISECONDS)
                    .addInterceptor(HttpLoggingInterceptor { message -> Log.d("ExampleApi", message) }
                            .setLevel(
                                    if (BuildConfig.DEBUG) {
                                        if (BuildConfig.FEATURE_OK_HTTP_VERBOSE) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BODY
                                    } else {
                                        HttpLoggingInterceptor.Level.BASIC
                                    }))
                    .build()

            val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()

            return Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .client(client)
                    .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.computation()))
                    .build()
                    .create(ExampleApi::class.java)
        }
    }

}