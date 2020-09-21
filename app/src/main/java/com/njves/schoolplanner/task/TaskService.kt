package com.njves.schoolplanner.task

import retrofit2.Response

interface TaskService {
    suspend fun getUserTaskList(id: String): Response<TaskListResponse>
}
