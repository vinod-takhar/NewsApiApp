package com.demoapps.newsapp.di

import android.util.Log
import com.demoapps.newsapp.BuildConfig
import com.demoapps.newsapp.data.network.NewsApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { headerInterceptor() }
    single { okhttpClient(get()) }
    single { retrofit(get()) }
    single { apiService(get()) }
}

fun apiService( retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)

fun retrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

fun okhttpClient(
    headerInterceptor: Interceptor
): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(headerInterceptor)
        .build()

fun headerInterceptor(): Interceptor =
    Interceptor { chain ->
        val request = chain.request()
        val newUrl = request.url.newBuilder()
            .addQueryParameter("apiKey", BuildConfig.NEWS_API_KEY)
//            .addQueryParameter("sortBy", "publishedAt")
//            .addQueryParameter("searchIn", "title")
            .addQueryParameter("language", "en")
            .build()

        Log.e("URL", newUrl.toString());

        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()
        chain.proceed(newRequest)
    }