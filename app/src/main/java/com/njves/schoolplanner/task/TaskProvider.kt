package com.njves.schoolplanner.task

import com.njves.schoolplanner.network.NetworkService
import com.njves.schoolplanner.profile.Profile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

object TaskProvider {
    private val service: TaskService = NetworkService.retrofit.create(TaskService::class.java)
    suspend fun getTaskList(id: String): Response<TaskListResponse> {
        return withContext(Dispatchers.IO){
            service.getUserTaskList(id)
        }
    }
    fun taskFactory(): List<Task>{
        val list = ArrayList<Task>()
        for (i: Int in 0.. 10){
            val task = Task("Name", "description", Profile(), Profile(),State("Невыполненно"), "Файлы", Date().time)
            list.add(task)
        }
        return list
    }

}