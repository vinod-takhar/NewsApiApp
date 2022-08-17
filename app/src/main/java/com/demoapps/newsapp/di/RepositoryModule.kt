package com.demoapps.newsapp.di

import com.demoapps.newsapp.data.network.NewsApi
import com.demoapps.newsapp.data.repository.ArticleRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { createRepository(get()) }
}

fun createRepository(
    newsApi: NewsApi
) : ArticleRepository = ArticleRepository(newsApi)