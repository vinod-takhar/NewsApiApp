package com.demoapps.newsapp.data.repository.paged

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.demoapps.newsapp.data.repository.ArticleRepository
import com.demoapps.newsapp.data.state.AppState
import com.demoapps.newsapp.model.Article

class ArticleSource(private val articleRepository: ArticleRepository,private val uiState: AppState) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            var nextPage = params.key ?: 1

            Log.e("LoadingPage## ${params.key}","page $nextPage");

            val articleListResponse = articleRepository.getArticles(uiState.searchQuery,uiState.searchIn,uiState.sortBy,uiState.searchFrom,nextPage)

            LoadResult.Page(
                data = articleListResponse.articles,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if(articleListResponse.articles.isNotEmpty())  ++nextPage else null
            )
        } catch (e: Exception) {
            Log.e("Error","Exception found...")
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
    
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return null
    }
}