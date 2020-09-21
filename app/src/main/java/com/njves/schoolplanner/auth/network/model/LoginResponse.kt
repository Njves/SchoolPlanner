package com.njves.schoolplanner.auth.network.model

import com.njves.schoolplanner.auth.Account

class LoginResponse(val data: Account, val error: String?){
    override fun toString(): String {
        return "LoginResponse(data=$data, error='$error')"
    }
}