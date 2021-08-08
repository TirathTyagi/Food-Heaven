package com.internshala.module3application.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
@Dao
interface LoginDao {
    @Insert
    fun userInsert(user:LoginInfo)
    @Query("SELECT * FROM Login_Info WHERE Phone =:phone")
    fun getUser(phone:String):LoginInfo
}