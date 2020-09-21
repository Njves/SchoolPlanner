package com.njves.schoolplanner.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.njves.schoolplanner.R
import com.njves.schoolplanner.auth.network.AuthorizationProvider
import com.njves.schoolplanner.menu.MainActivity
import com.njves.schoolplanner.model.School
import com.njves.schoolplanner.network.NetworkService
import com.njves.schoolplanner.preferences.AccountPreferences
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
        btnRegister.setOnClickListener{
            val account = Account(null,
                edEmail.text.toString(),
                edUsername.text.toString(),
                edPassword.text.toString(),
                spinnerSchoolNumber.selectedItem as School)
            registerAccount(it, account)
        }
    }

    private fun registerAccount(view: View, account: Account){
        CoroutineScope(Dispatchers.Main).launch{
            val response = AuthorizationProvider.registerAccount(account)
            if(response.isSuccessful){
                val body = response.body() ?: return@launch
                val error = body.error
                if(error != null) {
                    showMessage(view, error)
                    return@launch
                }
                authorizeAccount(body.data)
                showMessage(view, "Успешная регистрация")
            }else {
                Log.e("RegisterFragment", "Failure register! ${response.errorBody().toString()}")
                view?.let{
                    Snackbar.make(it, "Неудалось зарегистрироваться", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun showMessage(view: View,message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun getSchoolList(){
        CoroutineScope(Dispatchers.Main).launch {

            val response = NetworkService.getSchoolList()
            if (response.isSuccessful) {
                val schoolList = response.body() ?: return@launch
                if(schoolList.error != null){
                    showMessage(view ?: return@launch, schoolList.error)
                    return@launch
                }
                spinnerSchoolNumber.adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.support_simple_spinner_dropdown_item,
                    schoolList.data!!
                )
            }else{
                Toast.makeText(requireContext(), "Неудалось получить список школ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun authorizeAccount(account: Account){
        Log.d("RegisterFragment", account.toString())
        AccountPreferences(requireContext()).setAccount(Gson().toJson(account))
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }


}
