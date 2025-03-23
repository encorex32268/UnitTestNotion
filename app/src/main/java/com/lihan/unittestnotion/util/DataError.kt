package com.lihan.unittestnotion.util

sealed interface DataError: Error {

    enum class NetworkError: DataError{
        BAD_REQUEST,
        UNAUTHORIZED,
        NOT_FOUND,
        TIME_OUT,
        SERVER_ERROR,
        BAD_GATEWAY,
        UNKNOWN,
        NO_CONNECTION,
        CONNECTION_FAILED,
        NETWORK_FAILURE
    }
}