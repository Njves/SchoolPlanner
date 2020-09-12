package com.njves.schoolplanner.network.request

import com.njves.schoolplanner.model.School
import retrofit2.Response
import retrofit2.http.GET

interface SchoolService {
    @GET("api/get_school.php")
    fun getSchoolList(): Response<List<School>>
}
