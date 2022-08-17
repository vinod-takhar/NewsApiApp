package com.demoapps.newsapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleSource (
    val id: String?,
    val name: String?
) : Parcelable