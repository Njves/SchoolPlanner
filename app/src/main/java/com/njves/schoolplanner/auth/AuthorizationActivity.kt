package com.njves.schoolplanner.auth

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.njves.schoolplanner.R
import com.njves.schoolplanner.menu.MainActivity
import com.njves.schoolplanner.preferences.AccountPreferences

class AuthorizationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        checkExistAccount()
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_login, R.id.navigation_register
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navController.navigate(R.id.navigation_login)
    }
    private fun checkExistAccount(){

        val account = AccountPreferences.getInstance(this).getAccount()
        if(account != ""){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}