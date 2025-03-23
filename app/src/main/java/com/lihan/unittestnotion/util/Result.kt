package com.lihan.unittestnotion.util

sealed interface Result<out D,out E>{
    data class Success<D>(
        val data: D
    ): Result<D,Nothing>

    data class Error<E>(
        val error: com.lihan.unittestnotion.util.Error
    ): Result<Nothing,E>
}