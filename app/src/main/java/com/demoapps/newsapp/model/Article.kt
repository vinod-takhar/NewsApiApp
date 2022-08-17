package com.demoapps.newsapp.model

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val title: String?,
    val content: String?,
    val description: String?,
    val author: String?,
    val source: ArticleSource?,
    val url: String,
    val publishedAt: String?,
    val urlToImage: String?
): Parcelable {
    companion object NavigationType : NavType<Article>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): Article? {
            return bundle.getParcelable(key)
        }

        override fun parseValue(value: String): Article {
            return Gson().fromJson(value, Article::class.java)
        }

        override fun put(bundle: Bundle, key: String, value: Article) {
            bundle.putParcelable(key, value)
        }
    }
}