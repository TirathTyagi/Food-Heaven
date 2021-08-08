package com.internshala.module3application.Activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.internshala.module3application.R

class MainActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences(getString(R.string.login_preference), MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false)
        val phone = sharedPreferences.getString("PhoneNum",null)
        supportActionBar?.hide()
        println("the phone is: "+phone)
        println(isLoggedIn)
        Handler().postDelayed({
            if(isLoggedIn)
            {
                val intent = Intent(this@MainActivity,HomeActivity::class.java)
                intent.putExtra("Phone",phone)
                startActivity(intent)
                finish()
            }
            else {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        },2000)
    }
}