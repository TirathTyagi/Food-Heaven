package com.internshala.module3application.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import com.internshala.module3application.Database.LoginInfo
import com.internshala.module3application.Database.RestDatabase
import com.internshala.module3application.R

class LoginActivity : AppCompatActivity() {
    lateinit var phoneEntered:EditText
    lateinit var passEntered:EditText
    lateinit var buttonLogin:Button
    lateinit var forgotPass:TextView
    lateinit var register:TextView
    lateinit var sharedPreference:SharedPreferences
    lateinit var actualPhone:String
    lateinit var actualPass:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference = getSharedPreferences(getString(R.string.login_preference), Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreference.getBoolean("isLoggedIn",false)
        println(isLoggedIn)
        if(intent!=null)
        {
            actualPass = sharedPreference.getString("Password","admin").toString()
            actualPhone = sharedPreference.getString("PhoneNum","0000").toString()
        }
        if(isLoggedIn)
        {
            val intent = Intent(this@LoginActivity,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        setContentView(R.layout.activity_login)
        title = "Login"
        phoneEntered = findViewById(R.id.etPhoneDet)
        passEntered = findViewById(R.id.etPassword)
        buttonLogin = findViewById(R.id.btnLogin)
        forgotPass = findViewById(R.id.txtForgot)
        register = findViewById(R.id.txtRegister)
        buttonLogin.setOnClickListener{
            val enteredPass = passEntered.text.toString()
            val enteredMob = phoneEntered.text.toString()
            val getUser = GetUserInfo(this@LoginActivity,enteredMob).execute().get()
            if(getUser == null)
            {
                Toast.makeText(this@LoginActivity,"Please Register Yourself First",Toast.LENGTH_SHORT)
            }
            else {
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)

                if (enteredMob.equals(getUser.phone_num)) {
                    if (enteredPass.equals(getUser.passwords)) {
                        savePreferences()
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
        register.setOnClickListener {
            val intent = Intent(this@LoginActivity,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        forgotPass.setOnClickListener {
            val intent = Intent(this@LoginActivity,ForgotPassword::class.java)
            startActivity(intent)
            finish()
        }
    }
    fun savePreferences()
    {
        sharedPreference.edit().putString("PhoneNum",phoneEntered.text.toString()).apply()
        sharedPreference.edit().putBoolean("isLoggedIn", true).apply()
    }
    class GetUserInfo(val context:Context,val phoneRecieved:String):
        AsyncTask<Void, Void, LoginInfo>()
    {
        val db = Room.databaseBuilder(context, RestDatabase::class.java,"user-info").build()
        override fun doInBackground(vararg params: Void?): LoginInfo {
            val userGet = db.LoginDao().getUser(phoneRecieved)
            return userGet
        }
    }
}