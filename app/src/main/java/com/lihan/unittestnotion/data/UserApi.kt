package com.lihan.unittestnotion.data

import com.lihan.unittestnotion.domain.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface UserApi {
    @GET("/user/{id}")
    fun getUserInfo(@Path("id") id: Int): Call<User>
}