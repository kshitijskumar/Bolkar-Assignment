package com.example.bolkarassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bolkarassignment.remote.BolkarApi
import com.example.bolkarassignment.repository.MainRepository

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val api = BolkarApi.createApi()
        val repo = MainRepository(api)

        return MainViewModel(repo) as T
    }
}