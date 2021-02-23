package com.example.bolkarassignment.remote

import com.example.bolkarassignment.response.ApiResponseBody
import com.example.bolkarassignment.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface BolkarApi {

    @GET("live/room.json")
    suspend fun getInformation() : Response<ApiResponseBody>

    companion object{

        fun createApi() : BolkarApi {
            val client = OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(BolkarApi::class.java)
        }
    }
}