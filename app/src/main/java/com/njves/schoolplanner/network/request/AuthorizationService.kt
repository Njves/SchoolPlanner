package com.njves.schoolplanner.network.request

import com.njves.schoolplanner.auth.Account
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.POST

interface AuthorizationService {

    @POST("api/auth/login.php")
    fun loginAccount(@FieldMap dataMap: HashMap<String, String>): Response<Account>

    fun registerAccount(@Body account: Account): Response<Account>
}