package com.demoapps.newsapp.ui

import NewsAppNavHost
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import com.demoapps.newsapp.ui.screen.MainViewModel
import com.demoapps.newsapp.ui.theme.PagingComposeTheme
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModel()

	@OptIn(ExperimentalMaterialApi::class)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			PagingComposeTheme {
				NewsAppNavHost(mainViewModel)
			}
		}
	}
}