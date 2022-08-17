package com.demoapps.newsapp

import android.app.Application
import com.demoapps.newsapp.di.networkModule
import com.demoapps.newsapp.di.repositoryModule
import com.demoapps.newsapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@NewsApp)
            modules(listOf(networkModule, repositoryModule, viewModelModule))
        }
    }
}