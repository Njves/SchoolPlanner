package com.njves.schoolplanner.auth

import com.google.gson.annotations.SerializedName
import com.njves.schoolplanner.model.School

data class Account(val id: String?, val username: String, val password: String,val email: String,@SerializedName("school_number") val schoolNumber: School){
    override fun toString(): String {
        return "Account(id=$id, username='$username', password='$password', email='$email', schoolNumber=$schoolNumber)"
    }
}