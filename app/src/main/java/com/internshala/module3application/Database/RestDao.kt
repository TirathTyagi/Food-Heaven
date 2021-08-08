package com.internshala.module3application.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RestDao {
    @Insert
    fun restInsert(rest:FavRestaurants)
    @Delete
    fun restRemove(rest:FavRestaurants)
    @Query("SELECT * FROM Restaurants")
    fun getRests():List<FavRestaurants>
    @Query("SELECT * FROM Restaurants WHERE rest_id = :restId")
    fun searchFav(restId:String):FavRestaurants
}