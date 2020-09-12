package com.njves.schoolplanner.network


import com.njves.schoolplanner.auth.Account
import com.njves.schoolplanner.model.School
import com.njves.schoolplanner.network.request.AuthorizationService
import com.njves.schoolplanner.network.request.SchoolService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService{
    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okClient = OkHttpClient.Builder().addInterceptor(interceptor).retryOnConnectionFailure(true).build()

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://z960769g.beget.tech/SPlanner/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okClient)
        .build()



    suspend fun authAccount(email: String, password: String): Response<Account> {
        val hashMap = HashMap<String, String>()
        hashMap["email"] = email
        hashMap["password"]= password
        return withContext(Dispatchers.IO){
            retrofit.create(AuthorizationService::class.java).loginAccount(hashMap)
        }
    }

    suspend fun getSchoolList(): Response<List<School>>{
        return withContext(Dispatchers.IO){
            retrofit.create(SchoolService::class.java).getSchoolList()
        }
    }
}