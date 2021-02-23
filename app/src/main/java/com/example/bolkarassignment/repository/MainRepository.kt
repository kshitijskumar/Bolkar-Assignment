package com.example.bolkarassignment.repository

import com.example.bolkarassignment.remote.BolkarApi
import com.example.bolkarassignment.response.ApiResponseBody
import com.example.bolkarassignment.utils.Result

class MainRepository(
    private val api : BolkarApi
) : BaseRepository() {

    suspend fun getInformation() : Result<ApiResponseBody>{
        return safeApiCall { api.getInformation() }
    }
}