package com.njves.schoolplanner.network.request

import android.accounts.Account
import retrofit2.Call
import retrofit2.http.GET

interface AccountService {
    @GET("account/get?id")
    suspend fun getAccountById(): Account
}