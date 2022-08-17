package com.demoapps.newsapp.di

import androidx.lifecycle.SavedStateHandle
import com.demoapps.newsapp.ui.screen.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
//    viewModel { MainViewModel(savedState = SavedStateHandle(), articleRepository = get()) }
//    viewModel {params -> MainViewModel(params.get(),params.get())}
}
