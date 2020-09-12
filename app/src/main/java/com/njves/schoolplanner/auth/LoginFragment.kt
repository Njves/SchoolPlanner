package com.njves.schoolplanner.auth

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.njves.schoolplanner.R
import com.njves.schoolplanner.network.NetworkService
import com.njves.schoolplanner.network.request.AccountService
import com.njves.schoolplanner.preferences.AccountPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    private lateinit var edEmail: TextInputEditText
    private lateinit var edPassword: TextInputEditText
    private lateinit var btnLogin: Button
    private lateinit var btnForgotPassword: Button
    private lateinit var checkBoxRememberMe: CheckedTextView
    private lateinit var progressBarLoading: ProgressBar

    private var accountPreferences: AccountPreferences? = null
    private var gson = Gson()

    companion object{
        var TAG: String = "LoginFragment"
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        accountPreferences = AccountPreferences(context)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // ПОменять на емайл
        edEmail = view.findViewById(R.id.edName)
        edPassword = view.findViewById(R.id.edPassword)
        btnLogin = view.findViewById(R.id.btnLogin)
        btnForgotPassword = view.findViewById(R.id.btnForgotPassword)
        checkBoxRememberMe = view.findViewById(R.id.checkboxRememberMe)
        progressBarLoading = view.findViewById(R.id.progressBar)

        checkBoxRememberMe.setOnClickListener{
            checkBoxRememberMe.isChecked = !checkBoxRememberMe.isChecked
        }
        btnLogin.setOnClickListener{
            val email = edEmail.text.toString()
            val password = edPassword.text.toString()
            loginAccount(email,password)
        }
    }

    private fun loginAccount(email: String, password: String){
        Log.i("LoginFragment", "start login account")
        CoroutineScope(Dispatchers.Main).launch{
            val response = NetworkService.authAccount(email, password)
            Log.i("LoginFragment", "onResponse code:${response.code()}, message ${response.message()}, body: ${response.body().toString()}")
            if(response.isSuccessful) {
                val body = response.body() ?: return@launch
                accountPreferences?.setAccount(gson.toJson(body))
                if (checkBoxRememberMe.isChecked) {
                    rememberAccountEmail(body.email)
                }
            }else{
                Log.e("LoginFragment", "Failure login! ${response.errorBody().toString()}")
                view?.let{
                    Snackbar.make(it, "Неудалось залогироваться", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun rememberAccountEmail(email: String){

        Log.i(TAG, "Edit account email: $email")
        accountPreferences?.setEmail(email)

    }
}