package com.example.mealapp.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.example.mealapp.ApiEndpoint
import com.example.mealapp.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiConfig(context: Context) {

    private val chuckerCollector = ChuckerCollector(
        context = context,
        showNotification = true,
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )

    private val chuckerInterceptor = ChuckerInterceptor.Builder(context)
        .collector(chuckerCollector)
        .maxContentLength(250_000L)
        .alwaysReadResponseBody(true)
        .createShortcut(true)
        .build()

    private val client = OkHttpClient.Builder()
        .addInterceptor(chuckerInterceptor)
        .build()

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .build()

    val apiEndpoint: ApiEndpoint by lazy {
        retrofit.create(ApiEndpoint::class.java)
    }
}