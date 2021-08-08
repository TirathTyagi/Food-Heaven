package com.internshala.module3application.Activity

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.internshala.module3application.R

class DisplayInfo : AppCompatActivity() {
    lateinit var txtEmail:TextView
    lateinit var txtPhone:TextView
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_info)
        sharedPreferences = getSharedPreferences(getString(R.string.login_preference), MODE_PRIVATE)
        txtEmail = findViewById(R.id.txtEmailDisp)
        txtPhone = findViewById(R.id.txtPhoneDisp)
        val email = sharedPreferences.getString("Email","Null")
        val phone = sharedPreferences.getString("Phone","Null")
        txtPhone.text = phone
        txtEmail.text = email
    }
}