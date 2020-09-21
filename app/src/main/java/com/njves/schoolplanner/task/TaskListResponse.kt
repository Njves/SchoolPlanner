package com.njves.schoolplanner.task

class TaskListResponse(private val data: List<Task>?,private  val error: String?){
    override fun toString(): String {
        return "TaskListResponse(data=$data, error=$error)"
    }
}
