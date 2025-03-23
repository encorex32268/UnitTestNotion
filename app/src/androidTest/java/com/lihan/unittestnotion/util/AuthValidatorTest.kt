package com.lihan.unittestnotion.util

import com.google.common.truth.Truth
import org.junit.Test

class AuthValidatorTest{

    @Test
    fun testEmailIsEmpty(){
        val result = AuthValidator.isValidEmail(
            email = ""
        )
        Truth.assertThat(result).isFalse()
    }
    @Test
    fun testEmailIsNotFormat(){
        val result = AuthValidator.isValidEmail(
            email = "12344--fref@"
        )
        Truth.assertThat(result).isFalse()
    }
    @Test
    fun testEmailIsCorrect(){
        val result = AuthValidator.isValidEmail(
            email = "12@gmail.com"
        )
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun testPasswordIsEmpty(){
        val result = AuthValidator.isValidatePassword(
            password = ""
        )
        Truth.assertThat(result).isFalse()
    }
    @Test
    fun testPasswordLengthIsShort(){
        val result = AuthValidator.isValidatePassword(
            password = "1234567"
        )
        Truth.assertThat(result).isFalse()
    }
    @Test
    fun testPasswordIsCorrect(){
        val result = AuthValidator.isValidatePassword(
            password = "12345678"
        )
        Truth.assertThat(result).isTrue()
    }
}