package com.lihan.unittestnotion.data

import com.lihan.unittestnotion.domain.User
import com.lihan.unittestnotion.domain.UserRepository
import com.lihan.unittestnotion.util.DataError
import com.lihan.unittestnotion.util.Result
import io.ktor.network.sockets.SocketTimeoutException
import kotlinx.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException

class UserRepositoryImpl(
    private val userApi: UserApi
): UserRepository{
    override fun getUserInfo(userId: Int): Result<User, DataError.NetworkError>{
       return try {
            val result = userApi.getUserInfo(userId).execute()
            if (result.isSuccessful){
                result.body()?.let {
                    Result.Success(it)
                }?:run {
                    Result.Error(DataError.NetworkError.UNKNOWN)
                }

            }else{
                Result.Error(handleErrorCode(result.code()))
            }
        }catch (_: SocketTimeoutException){
            Result.Error(DataError.NetworkError.TIME_OUT)
       } catch (e: UnknownHostException) {
           Result.Error(DataError.NetworkError.NO_CONNECTION)
       } catch (e: ConnectException) {
           Result.Error(DataError.NetworkError.CONNECTION_FAILED)
       } catch (e: IOException) {
           Result.Error(DataError.NetworkError.NETWORK_FAILURE)
       }
    }

    private fun handleErrorCode(code: Int): DataError.NetworkError {
        return when(code){
            400 -> DataError.NetworkError.BAD_REQUEST
            401 -> DataError.NetworkError.UNAUTHORIZED
            404 -> DataError.NetworkError.NOT_FOUND
            408 -> DataError.NetworkError.TIME_OUT
            500 -> DataError.NetworkError.SERVER_ERROR
            502 -> DataError.NetworkError.BAD_GATEWAY
            else -> DataError.NetworkError.UNKNOWN
        }
    }
}