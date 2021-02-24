package com.example.bolkarassignment.repository

import com.example.bolkarassignment.remote.BolkarApi
import com.example.bolkarassignment.response.ApiResponseBody
import com.example.bolkarassignment.utils.Result
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class MainRepositoryTest {

    lateinit var api : BolkarApi
    lateinit var repo : MainRepository

    @Before
    fun setup() {
        api = mock()
        repo = MainRepository(api)
    }

    @Test
    fun getInformation_onSuccess_returnsResultSuccess() = runBlocking {
        whenever(api.getInformation()).thenReturn(Response.success(ApiResponseBody()))

        val result = repo.getInformation()

        assertEquals(Result.Success(ApiResponseBody()), result)
    }
}