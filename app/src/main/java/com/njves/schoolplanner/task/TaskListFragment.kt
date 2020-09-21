package com.njves.schoolplanner.task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.njves.schoolplanner.R
import com.njves.schoolplanner.preferences.AccountPreferences
import kotlinx.android.synthetic.main.fragment_task_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class TaskListFragment: Fragment() {
    private lateinit var accountPreferences: AccountPreferences
    private lateinit var rvList: RecyclerView
    private lateinit var tvMessage: TextView
    private lateinit var pbLoading: ProgressBar

    private var taskList: List<Task>? = null
    private var type = true
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*accountPreferences = AccountPreferences.getInstance(requireContext())
        CoroutineScope(Dispatchers.Main).launch {
            val response = TaskProvider.getTaskList(accountPreferences.getId())
            if(checkResponse(response)){
                val taskList = response.body()

            }
        }*/
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvList = view.findViewById(R.id.rvList)
        tvMessage = view.findViewById(R.id.tvMessage)
        pbLoading = view.findViewById(R.id.pbLoading)
        val btnChangeList = view.findViewById<Button>(R.id.btnChangeList)
        rvList.layoutManager = LinearLayoutManager(requireContext())
        setAdapter()
        btnChangeList.setOnClickListener{
            type = !type
            setAdapter()
        }
    }
    private fun setAdapter(){
        rvList.adapter = TaskAdapter(requireContext(), TaskProvider.taskFactory())
        changeLayoutManager()
    }
    private fun changeLayoutManager(){
        when(type){
            true->{
                rvList.layoutManager = LinearLayoutManager(requireContext())
            }
            false->{
                rvList.layoutManager = GridLayoutManager(requireContext(), 2)
            }

        }
    }
    private fun checkResponse(response: Response<TaskListResponse>): Boolean{
        val code = response.code()
        val errorBody = response.errorBody()?.string()
        Log.e("TaskListFragment", code.toString())
        errorBody?.let {
            Log.e("TaskListFragment", it)
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            return false
        }
        return response.isSuccessful
    }

    private fun updateAdapter(list: List<Task>){

    }


}