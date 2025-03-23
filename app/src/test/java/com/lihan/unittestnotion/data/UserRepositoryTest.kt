package com.lihan.unittestnotion.data

import com.google.common.truth.Truth.assertThat
import com.lihan.unittestnotion.domain.User
import com.lihan.unittestnotion.domain.UserRepository
import com.lihan.unittestnotion.util.DataError
import com.lihan.unittestnotion.util.Result
import io.ktor.network.sockets.SocketTimeoutException
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration

class UserRepositoryTest{

    private val okHttpClient = OkHttpClient.Builder()
        .writeTimeout(Duration.ofSeconds(30))
        .readTimeout(Duration.ofSeconds(1))
        .build()

    private val mockWebServer = MockWebServer()
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp(){
        val userApi: UserApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(UserApi::class.java)

        userRepository = FakeUserRepositoryImpl(userApi)
    }

    @After
    fun tearDown(){
        mockWebServer.close()
    }

    @Test
    fun testGetUserInfoWhenSuccess(){
        val responseUser = User(id = 101, name = "Andy", age = 18)
        mockWebServer.enqueue(
            response = MockResponse()
                .setBody(Json.encodeToString(responseUser))
                .setResponseCode(200)
        )
        val result = userRepository.getUserInfo(userId = 101)
        assertThat(result is Result.Success).isTrue()
        val data = (result as Result.Success).data
        assertThat(data.name).isEqualTo("Andy")
        assertThat(data.age).isEqualTo(18)

    }

    @Test
    fun testGetUserInfoError400(){
        val responseUser = User(id = 101, name = "Andy", age = 18)
        mockWebServer.enqueue(
            response = MockResponse()
                .setBody(Json.encodeToString(responseUser))
                .setResponseCode(400)
        )
        val result = userRepository.getUserInfo(userId = 101)
        assertThat(result is Result.Error).isTrue()
        val resultError = result as Result.Error
        assertThat(resultError.error).isEqualTo(DataError.NetworkError.BAD_REQUEST)
    }

    @Test
    fun testGetUserInfoError401(){
        val responseUser = User(id = 101, name = "Andy", age = 18)
        mockWebServer.enqueue(
            response = MockResponse()
                .setBody(Json.encodeToString(responseUser))
                .setResponseCode(401)
        )
        val result = userRepository.getUserInfo(userId = 101)
        assertThat(result is Result.Error).isTrue()
        val resultError = result as Result.Error
        assertThat(resultError.error).isEqualTo(DataError.NetworkError.UNAUTHORIZED)
    }

    @Test
    fun testGetUserInfoError404(){
        val responseUser = User(id = 101, name = "Andy", age = 18)
        mockWebServer.enqueue(
            response = MockResponse()
                .setBody(Json.encodeToString(responseUser))
                .setResponseCode(404)
        )
        val result = userRepository.getUserInfo(userId = 101)
        assertThat(result is Result.Error).isTrue()
        val resultError = result as Result.Error
        assertThat(resultError.error).isEqualTo(DataError.NetworkError.NOT_FOUND)
    }

    @Test
    fun testGetUserInfoError408(){
        mockWebServer.enqueue(
            response = MockResponse()
                .setBody("")
                .setResponseCode(408)
        )
        try {
            val result = userRepository.getUserInfo(userId = 101)
            assertThat(result is Result.Error).isTrue()
            val resultError = result as Result.Error
            assertThat(resultError.error).isEqualTo(DataError.NetworkError.TIME_OUT)
        }catch (_: SocketTimeoutException){

        }
    }

    @Test
    fun testGetUserInfoError500(){
        val responseUser = User(id = 101, name = "Andy", age = 18)
        mockWebServer.enqueue(
            response = MockResponse()
                .setBody(Json.encodeToString(responseUser))
                .setResponseCode(500)
        )
        val result = userRepository.getUserInfo(userId = 101)
        assertThat(result is Result.Error).isTrue()
        val resultError = result as Result.Error
        assertThat(resultError.error).isEqualTo(DataError.NetworkError.SERVER_ERROR)
    }

    @Test
    fun testGetUserInfoError502(){
        val responseUser = User(id = 101, name = "Andy", age = 18)
        mockWebServer.enqueue(
            response = MockResponse()
                .setBody(Json.encodeToString(responseUser))
                .setResponseCode(502)
        )
        val result = userRepository.getUserInfo(userId = 101)
        assertThat(result is Result.Error).isTrue()
        val resultError = result as Result.Error
        assertThat(resultError.error).isEqualTo(DataError.NetworkError.BAD_GATEWAY)
    }

    @Test
    fun testGetUserInfoError999(){
        val responseUser = User(id = 101, name = "Andy", age = 18)
        mockWebServer.enqueue(
            response = MockResponse()
                .setBody(Json.encodeToString(responseUser))
                .setResponseCode(999)
        )
        val result = userRepository.getUserInfo(userId = 101)
        assertThat(result is Result.Error).isTrue()
        val resultError = result as Result.Error
        assertThat(resultError.error).isEqualTo(DataError.NetworkError.UNKNOWN)
    }

}