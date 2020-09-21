package com.njves.schoolplanner.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckedTextView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.njves.schoolplanner.R
import com.njves.schoolplanner.auth.network.AuthorizationProvider
import com.njves.schoolplanner.menu.MainActivity
import com.njves.schoolplanner.preferences.AccountPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var edEmail: TextInputEditText
    private lateinit var edPassword: TextInputEditText
    private lateinit var btnLogin: Button
    private lateinit var btnForgotPassword: Button
    private lateinit var checkBoxRememberMe: CheckedTextView
    private lateinit var progressBarLoading: ProgressBar
    private lateinit var btnToRegister: Button

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
        edEmail = view.findViewById(R.id.edEmail)
        edPassword = view.findViewById(R.id.edPassword)
        btnLogin = view.findViewById(R.id.btnLogin)
        btnForgotPassword = view.findViewById(R.id.btnForgotPassword)
        checkBoxRememberMe = view.findViewById(R.id.checkboxRememberMe)
        progressBarLoading = view.findViewById(R.id.progressBar)
        btnToRegister = view.findViewById(R.id.btnToRegister)

        checkBoxRememberMe.setOnClickListener{
            checkBoxRememberMe.isChecked = !checkBoxRememberMe.isChecked
        }
        btnToRegister.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_login_to_navigation_register)
        }
        btnLogin.setOnClickListener{
            val email = edEmail.text.toString()
            val password = edPassword.text.toString()
            loginAccount(email,password)
        }
    }

    private fun loginAccount(email: String, password: String){
        Log.i("LoginFragment", "start login account")
        val job  =CoroutineScope(Dispatchers.Main).launch{
            val response = AuthorizationProvider.authAccount(email, password)

            if(response.isSuccessful) {
                val body = response.body() ?: return@launch
                if(body.error != null) {
                    showMessage(view ?: return@launch, body.error)
                    return@launch
                }
                val account = body.data
                authorizeAccount(checkBoxRememberMe.isChecked, account)

            }
            else {
                Log.e("LoginFragment", "Failure login! ${response.errorBody().toString()}")
                view?.let{
                    Snackbar.make(it, response.message(), Snackbar.LENGTH_SHORT).show()
                }
            }
        }

    }
    private fun showMessage(view: View,message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
    private fun authorizeAccount(isSaveEmail: Boolean,account: Account){
        Log.d("LoginFragment", "Authorize account")

        val pref = AccountPreferences(requireContext())
        pref.setAccount(Gson().toJson(account))
        if(isSaveEmail){
            rememberAccountEmail(account.email)
        }
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun rememberAccountEmail(email: String){
        Log.i(TAG, "Edit account email: $email")
        accountPreferences?.setEmail(email)

    }
}