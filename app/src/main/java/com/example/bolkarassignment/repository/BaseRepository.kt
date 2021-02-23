package com.example.bolkarassignment.repository

import android.util.Log
import com.example.bolkarassignment.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception

abstract class BaseRepository {

    suspend fun <T>safeApiCall(call : suspend () -> Response<T>) : Result<T> {

        return withContext(Dispatchers.IO){
            try {
                val response = call.invoke()
                if (response.isSuccessful && response.body() != null){
                    Result.Success(response.body()!!)
                }else{
                    when(response.code()){
                        400 -> Result.Error("Bad request. Try again later")
                        500 -> Result.Error("Something went wrong on our side")
                        else -> Result.Error("Something went wrong error")
                    }
                }

            } catch (e : Exception){
                Result.Error("Something went wrong: ${e.localizedMessage}")
            }
        }

    }
}