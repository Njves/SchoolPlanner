package com.njves.schoolplanner.task

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.njves.schoolplanner.R
import java.util.*

class TaskAdapter(private val context: Context,private val taskList: List<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
        private val tvDate = itemView.findViewById<TextView>(R.id.tvDate)
        private val tvState = itemView.findViewById<TextView>(R.id.tvState)
        fun bind(task: Task){
            tvTitle.text = task.name
            tvDescription.text = task.description
            tvDate.text = Date(task.date).toString()
            tvState.text = task.state.name
        }
    }


}