package com.njves.schoolplanner.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.njves.schoolplanner.R
import com.njves.schoolplanner.model.School
import com.njves.schoolplanner.network.NetworkService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private lateinit var progressBar: ProgressBar
    private lateinit var spinnerSchoolNumber: Spinner
    private lateinit var edEmail: TextInputEditText
    private lateinit var edPassword: TextInputEditText
    private lateinit var edPasswordRetry: TextInputEditText

    private lateinit var edUsername: TextInputEditText
    private lateinit var btnRegister: Button
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinnerSchoolNumber = view.findViewById(R.id.spinnerSchoolNumber)
        edEmail = view.findViewById(R.id.edEmail)
        edPassword = view.findViewById(R.id.edPassword)
        edPasswordRetry = view.findViewById(R.id.edPasswordRetry)
        edUsername = view.findViewById(R.id.edUsername)
        btnRegister = view.findViewById(R.id.btnRegister)
        progressBar = view.findViewById(R.id.progressBar)
        getSchoolList()
    }

    fun registerAccount(email: String, username: String, password: String, passwordRetry: String, schoolNumber: School){

    }

    fun getSchoolList(){
        CoroutineScope(Dispatchers.Main).launch{
            val responseSchoolList = NetworkService.getSchoolList()
            if(responseSchoolList.isSuccessful){
                spinnerSchoolNumber.adapter = SchoolAdapter(this@RegisterFragment.requireContext(),R.layout.item_school, responseSchoolList.body() ?: return@launch)
            }
        }

    }
    inner class SchoolAdapter(context: Context, private val resource: Int, private val schoolList: List<School>) : ArrayAdapter<School>(context, resource, schoolList) {
        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            return getCustomView(position, convertView, parent)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            return getCustomView(position, convertView, parent)
        }

        private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup ): View {
            val inflater = layoutInflater;
            if(convertView == null){
                val itemSchool = inflater.inflate(R.layout.item_school, parent, false)
            }else{
                val tvTitle = convertView.findViewById<TextView>(R.id.tvSchoolTitle)
                tvTitle.text = schoolList[position].title
            }


            return convertView!!
        }

    }
}