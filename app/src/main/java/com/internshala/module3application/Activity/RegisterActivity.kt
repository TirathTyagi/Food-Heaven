package com.internshala.module3application.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import com.internshala.module3application.Database.LoginInfo
import com.internshala.module3application.Database.RestDatabase
import com.internshala.module3application.R
import java.lang.Exception
import java.util.*

class RegisterActivity : AppCompatActivity() {
    lateinit var etName:EditText
    lateinit var etEmail:EditText
    lateinit var etMobile:EditText
    lateinit var etAddress:EditText
    lateinit var etPass:EditText
    lateinit var etConf:EditText
    lateinit var regButton:Button
    lateinit var sharedPreference: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        sharedPreference = getSharedPreferences(getString(R.string.login_preference), MODE_PRIVATE)
        title = "Registration"
        etName = findViewById(R.id.etNameAdd)
        etEmail = findViewById(R.id.etEmailAdd)
        etMobile = findViewById(R.id.etMobileAdd)
        etAddress = findViewById(R.id.etAddressAdd)
        etPass = findViewById(R.id.etPassAdd)
        etConf = findViewById(R.id.etConfPass)
        regButton = findViewById(R.id.btnRegister)
        regButton.setOnClickListener {
            try {
                val name = etName.text.toString()
                val email = etEmail.text.toString()
                val mobile = etMobile.text.toString()
                val address = etAddress.text.toString()
                val pass = etPass.text.toString()
                val conf = etConf.text.toString()
                if(pass.equals(conf))
                {
                    val login = LoginInfo(mobile,name,email,address,pass)
                    val addUser = AddUser(this@RegisterActivity,login).execute()
                    val checkUser = addUser.get()
                    if(checkUser)
                    {
                        Toast.makeText(this@RegisterActivity,"Registration Successful",Toast.LENGTH_SHORT)
                        val intent = Intent(this@RegisterActivity,LoginActivity::class.java)
                        savePreferences(mobile,pass)
                        println("Above startActivity")
                        startActivity(intent)
                        finish()
                    }
                    else
                    {
                        Toast.makeText(this@RegisterActivity,"Error in Registration",Toast.LENGTH_SHORT)
                    }
                }
                else
                {
                    Toast.makeText(this@RegisterActivity,"Passwords do not match",Toast.LENGTH_SHORT)
                }
            }
            catch (e:Exception)
            {
                Toast.makeText(this@RegisterActivity,"Fill all the details",Toast.LENGTH_SHORT)
            }
        }
    }
    fun savePreferences(mobile:String,pass:String)
    {
        sharedPreference.edit().putString("PhoneNum",mobile).apply()
        sharedPreference.edit().putString("Password",pass).apply()
    }
    class AddUser(val context:Context,val user:LoginInfo):AsyncTask<Void,Void,Boolean>()
    {
        val db = Room.databaseBuilder(context,RestDatabase::class.java,"user-info").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            db.LoginDao().userInsert(user)
            db.close()
            return true
        }
    }
}