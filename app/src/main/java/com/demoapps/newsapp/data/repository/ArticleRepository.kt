package com.demoapps.newsapp.data.repository

import com.demoapps.newsapp.data.network.NewsApi

class ArticleRepository(private val newsApi: NewsApi) {
    suspend fun getArticles(searchQuery: String,searchIn: String,sortBy: String,searchFrom: String,page: Int) = newsApi.getArticles(searchQuery,searchIn,sortBy,searchFrom,page,100)
}