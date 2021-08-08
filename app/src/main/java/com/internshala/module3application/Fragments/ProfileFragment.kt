package com.internshala.module3application.Fragments

import android.content.Context

import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.internshala.module3application.Activity.HomeActivity
import com.internshala.module3application.Activity.RegisterActivity
import com.internshala.module3application.Database.LoginInfo
import com.internshala.module3application.Database.RestDatabase
import com.internshala.module3application.R
import java.net.ConnectException


class ProfileFragment : Fragment() {
    lateinit var profileName:TextView
    lateinit var profileNum:TextView
    lateinit var profileEmail:TextView
    lateinit var profileAddress:TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_profile, container, false)
        val phoneRecieved = (activity as HomeActivity).phone
        profileName = view.findViewById(R.id.txtProfileName)
        profileNum = view.findViewById(R.id.txtProfilePhone)
        profileEmail = view.findViewById(R.id.txtProfileEmail)
        profileAddress = view.findViewById(R.id.txtProfileAddress)
        val userExtract = GetUserInfo(activity as Context,phoneRecieved).execute().get()
        profileName.text = userExtract.name
        profileAddress.text = userExtract.delivery
        profileEmail.text = userExtract.email
        profileNum.text = userExtract.phone_num
        return view
    }
    class GetUserInfo(val context:Context,val phoneRecieved:String):AsyncTask<Void,Void,LoginInfo>()
    {
        val db = Room.databaseBuilder(context,RestDatabase::class.java,"user-info").build()
        override fun doInBackground(vararg params: Void?): LoginInfo {
            val userGet = db.LoginDao().getUser(phoneRecieved)
            return userGet
        }
    }
}