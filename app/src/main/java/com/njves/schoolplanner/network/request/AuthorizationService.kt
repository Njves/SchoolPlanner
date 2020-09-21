package com.njves.schoolplanner.network.request

import com.njves.schoolplanner.auth.Account

import com.njves.schoolplanner.auth.network.model.LoginRequest
import com.njves.schoolplanner.auth.network.model.LoginResponse
import com.njves.schoolplanner.auth.network.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthorizationService {

    @POST("api/auth/login.php")
    suspend fun loginAccount(@Body request: LoginRequest): Response<LoginResponse>
    @POST("api/auth/register.php")
    suspend fun registerAccount(@Body account: Account): Response<RegisterResponse>
}