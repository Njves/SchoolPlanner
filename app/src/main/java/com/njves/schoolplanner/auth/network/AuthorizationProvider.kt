package com.njves.schoolplanner.auth.network

import com.njves.schoolplanner.auth.Account
import com.njves.schoolplanner.auth.network.model.LoginRequest
import com.njves.schoolplanner.auth.network.model.LoginResponse
import com.njves.schoolplanner.auth.network.model.RegisterResponse
import com.njves.schoolplanner.network.NetworkService
import com.njves.schoolplanner.network.request.AuthorizationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

object AuthorizationProvider {

    suspend fun authAccount(email: String, password: String): Response<LoginResponse> {
        return withContext(Dispatchers.IO){
            NetworkService.retrofit.create(AuthorizationService::class.java).loginAccount(LoginRequest(email, password))
        }
    }

    suspend fun registerAccount(account: Account): Response<RegisterResponse>{
        return withContext(Dispatchers.IO){
            NetworkService.retrofit.create(AuthorizationService::class.java).registerAccount(account)
        }
    }
}