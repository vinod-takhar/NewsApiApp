package com.demoapps.newsapp.data.network

import com.demoapps.newsapp.model.ArticleListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("/v2/everything")
    suspend fun getArticles(
        @Query("q") q: String,
        @Query("searchIn") searchIn: String,
        @Query("sortBy") sortBy: String,
        @Query("from") from: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): ArticleListResponse
}