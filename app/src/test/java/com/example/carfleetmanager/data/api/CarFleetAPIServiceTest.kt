package com.example.carfleetmanager.data.api

import com.example.carfleetmanager.BuildConfig
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CarFleetAPIServiceTest {

    private lateinit var service: CarFleetAPIService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CarFleetAPIService::class.java)
    }

    private fun enqueueMockResponse(fileName: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()

        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun getCarList_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("Cars_API_Response.json")
            val responseBody = service.getCarList(BuildConfig.API_KEY).body()
            val request = server.takeRequest()

            assertThat(responseBody).isNotNull()
            Truth.assertThat(request.path).isEqualTo("/rest/car-list?apikey=${BuildConfig.API_KEY}")
        }
    }

    @Test
    fun getOwnerList_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("Owners_API_Response.json")
            val responseBody = service.getOwnerList(BuildConfig.API_KEY).body()
            val request = server.takeRequest()

            assertThat(responseBody).isNotNull()
            Truth.assertThat(request.path).isEqualTo("/rest/person-list?apikey=${BuildConfig.API_KEY}")
        }
    }

    @Test
    fun getCarList_receivedResponse_correctContent() {
        runBlocking {
            enqueueMockResponse("Cars_API_Response.json")
            val carList = service.getCarList(BuildConfig.API_KEY).body()!!
            val car = carList[0]

            assertThat(car.brand).isEqualTo("Opel")
            assertThat(car.model).isEqualTo("Astra")
            assertThat(car.registration).isEqualTo("WA12345")
        }
    }

    @Test
    fun getOwnerList_receivedResponse_correctContent() {
        runBlocking {
            enqueueMockResponse("Owners_API_Response.json")
            val ownerList = service.getOwnerList(BuildConfig.API_KEY).body()!!
            val owner = ownerList[0]

            assertThat(owner.firstName).isEqualTo("Libby")
            assertThat(owner.lastName).isEqualTo("Predovic")
        }
    }

}