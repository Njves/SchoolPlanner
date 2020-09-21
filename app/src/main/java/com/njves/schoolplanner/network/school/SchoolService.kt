package com.njves.schoolplanner.network.school


import retrofit2.Response
import retrofit2.http.GET

interface SchoolService {
    @GET("api/db/get_schools.php")
    suspend fun getSchoolList(): Response<SchoolResponse>
}

