package com.example.bolkarassignment.remote

import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BolkarApiTest {

    private lateinit var api : BolkarApi

    @Before
    fun setup() {
        api = BolkarApi.createApi()
    }

    @Test
    fun getInformation_onSuccess_returnsNonNullBody() = runBlocking {

        val response = api.getInformation()
        assertNotNull(response.body())
    }

}