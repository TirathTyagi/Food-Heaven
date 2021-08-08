package com.internshala.module3application.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Login_Info")
data class LoginInfo(
    @PrimaryKey @ColumnInfo(name = "Phone") val phone_num:String,
    @ColumnInfo(name = "Name") val name:String,
    @ColumnInfo(name = "Email_Address") val email:String,
    @ColumnInfo(name = "Delivery_Address") val delivery:String,
    @ColumnInfo(name = "Passwords") val passwords:String
)