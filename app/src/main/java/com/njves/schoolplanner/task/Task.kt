package com.njves.schoolplanner.task

import com.google.gson.annotations.SerializedName
import com.njves.schoolplanner.profile.Profile

data class Task(
    val name: String,
    val description: String,
    val from: Profile,
    val to: Profile,
    val state: State,
    @SerializedName("bind_files") private val bindFiles: String,
    val date: Long
)
