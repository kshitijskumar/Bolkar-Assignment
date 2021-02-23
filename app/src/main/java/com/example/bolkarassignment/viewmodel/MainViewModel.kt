package com.example.bolkarassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bolkarassignment.repository.MainRepository
import com.example.bolkarassignment.response.ApiResponseBody
import com.example.bolkarassignment.utils.Result
import kotlinx.coroutines.launch

class MainViewModel(
    private val repo : MainRepository
) : ViewModel() {

    private val _information = MutableLiveData<Result<ApiResponseBody>>()
    val information : LiveData<Result<ApiResponseBody>>
        get() = _information

    init {
        getInformation()
    }

    private fun getInformation() = viewModelScope.launch {
        _information.value = Result.Loading
        val result = repo.getInformation()
        _information.postValue(result)
    }
}