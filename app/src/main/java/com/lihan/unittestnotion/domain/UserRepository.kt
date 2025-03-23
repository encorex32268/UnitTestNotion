package com.lihan.unittestnotion.domain

import com.lihan.unittestnotion.util.DataError
import com.lihan.unittestnotion.util.Result

interface UserRepository {
    fun getUserInfo(userId: Int): Result<User,DataError.NetworkError>
}