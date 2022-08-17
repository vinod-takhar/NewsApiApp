package com.demoapps.newsapp.model

import com.google.gson.annotations.SerializedName

data class ArticleListResponse(
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("articles") val articles: List<Article>,
    @SerializedName("status") val status: String
)