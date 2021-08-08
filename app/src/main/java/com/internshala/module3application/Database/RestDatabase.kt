package com.internshala.module3application.Database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavRestaurants::class,LoginInfo::class],version = 2)
abstract class RestDatabase:RoomDatabase() {
    abstract fun RestDao():RestDao
    abstract fun LoginDao():LoginDao
}