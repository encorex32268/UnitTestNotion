package com.lihan.unittestnotion.util

import android.util.Patterns

object AuthValidator {

    /**
     *  Check:
     *   - Not Empty
     *   - Email Format
     */
    fun isValidEmail(email: String): Boolean{
        if (email.isEmpty()){
            return false
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     *  Check:
     *   - Password Length
     *   - Not Empty
     */
    fun isValidatePassword(password: String): Boolean {
        if (password.length < 8){
            return false
        }
        if (password.isEmpty()) {
            return false
        }
        return true
    }
}