package com.njves.schoolplanner.preferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class AccountPreferences(private val context: Context) {
    private lateinit var preferences: SharedPreferences
    companion object{
        const val PREF_NAME = "account_pref"
        const val ID = "id"
        const val EMAIL = "email"
        const val ACCOUNT = "account"
    }
    init{
        preferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)

    }
    fun getId(): String = preferences.getString(ID, "") ?: ""

    fun setId(id: String){
        val editor = preferences.edit()
        editor.putString(ID, id)
        editor.apply()
    }

    fun getEmail(): String = preferences.getString(EMAIL, "") ?: ""

    fun setEmail(email: String){
        val editor = preferences.edit()
        editor.putString(EMAIL, email)
        editor.apply()
    }

    fun removeEmail() = preferences.edit().remove(EMAIL).apply()
    // Получаю JSON
    fun getAccount(): String = preferences.getString(ACCOUNT, "") ?: ""

    // Передаю JSON
    fun setAccount(account: String){
        val editor = preferences.edit()
        editor.putString(ACCOUNT, account)
        editor.apply()
    }


}