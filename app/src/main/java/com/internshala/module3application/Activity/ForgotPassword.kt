package com.internshala.module3application.Activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.internshala.module3application.R

class ForgotPassword : AppCompatActivity() {
    lateinit var etPhone:EditText
    lateinit var etEmail:EditText
    lateinit var btnForgot:Button
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        sharedPreferences = getSharedPreferences(getString(R.string.login_preference), MODE_PRIVATE)
        etPhone = findViewById(R.id.etMobForgot)
        etEmail = findViewById(R.id.etEmailForgot)
        btnForgot = findViewById(R.id.btnForgotPass)
        btnForgot.setOnClickListener {
            val email = etEmail.text.toString()
            val phone = etPhone.text.toString()
            savePreferences(phone,email)
            val intent = Intent(this@ForgotPassword,DisplayInfo::class.java)
            startActivity(intent)
            finish()
        }
    }
    fun savePreferences(phone:String,email:String)
    {
        sharedPreferences.edit().putString("Phone",phone).apply()
        sharedPreferences.edit().putString("Email",email).apply()
    }
}