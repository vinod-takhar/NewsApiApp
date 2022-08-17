package com.demoapps.newsapp.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.demoapps.newsapp.data.repository.ArticleRepository
import com.demoapps.newsapp.data.repository.paged.ArticleSource
import com.demoapps.newsapp.data.state.AppState
import com.demoapps.newsapp.model.Article
import kotlinx.coroutines.flow.Flow

class MainViewModel(
    val articleRepository: ArticleRepository
) : ViewModel() {

    var uiOptionsForFilterState by mutableStateOf(AppState())
        private set

    lateinit var articleResource: ArticleSource;

    val articles: Flow<PagingData<Article>> = Pager(PagingConfig(pageSize = 100)) {
//        ArticleSource(articleRepository,uiState)
        getArticleResourceObj()
    }.flow

    private fun getArticleResourceObj(): ArticleSource{
        articleResource = ArticleSource(articleRepository,uiOptionsForFilterState)
        return articleResource;
    }

//    @Composable
    fun updateAppState(newAppState: AppState) {
        uiOptionsForFilterState = newAppState
        articleResource.invalidate()
    }
}