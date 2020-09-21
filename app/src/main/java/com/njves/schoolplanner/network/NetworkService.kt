package com.njves.schoolplanner.network


import com.njves.schoolplanner.network.school.SchoolResponse
import com.njves.schoolplanner.network.school.SchoolService
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

    val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://host1820248.hostland.pro/SPlanner/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okClient)
        .build()





    suspend fun getSchoolList(): Response<SchoolResponse> {
        return withContext(Dispatchers.IO){
            retrofit.create(SchoolService::class.java).getSchoolList()
        }
    }
}