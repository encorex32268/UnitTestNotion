package com.lihan.unittestnotion

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.request.url
import kotlinx.coroutines.runBlocking

val client = HttpClient(CIO){
    install(HttpSend){
        request {
            headers.remove("name")
            headers.append("name","1234")
        }
    }
}

fun main() = runBlocking {
    val response: String = client.get {
        url("https://httpbin.org/get")
        headers {
            set("name","12345")
        }
    }.body<String>()
    println(response)
}