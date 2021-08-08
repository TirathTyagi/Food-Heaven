package com.internshala.module3application.Adapter

import android.content.Context
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.internshala.module3application.Database.FavRestaurants
import com.internshala.module3application.Database.RestDatabase
import com.internshala.module3application.R
import com.squareup.picasso.Picasso

class DashboardAdapter(val Context:Context,val itemList:ArrayList<FavRestaurants>):RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {
    class DashboardViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        val restName:TextView = view.findViewById(R.id.txtRestaurantName)
        val restPrice:TextView = view.findViewById(R.id.txtPricePerson)
        val imgRest:ImageView = view.findViewById(R.id.imgRestaurant)
        val restRating:TextView = view.findViewById(R.id.txtRating)
        val loveIcon:ImageView = view.findViewById(R.id.imgFavIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_dashboard,parent,false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val restaurants = itemList[position]
        holder.restName.text = restaurants.restName
        holder.restPrice.text = restaurants.restPrice
        holder.restRating.text = restaurants.restRating
        Picasso.get().load(restaurants.restImg).error(R.drawable.ic_default_rest).into(holder.imgRest)
        if(DBASyncTask(Context,restaurants,1).execute().get())
        {
            holder.loveIcon.setImageResource(R.drawable.ic_love1_icon)
        }
        else
        {
            holder.loveIcon.setImageResource(R.drawable.ic_love2_icon)
        }
        holder.loveIcon.setOnClickListener {
            if(!DBASyncTask(Context,restaurants,1).execute().get())
            {
                holder.loveIcon.setImageResource(R.drawable.ic_love1_icon)
                val restIns = DBASyncTask(Context,restaurants,2).execute()
                val checkIns = restIns.get()
                if(checkIns)
                {
                    Toast.makeText(Context,"Added to Favourites",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(Context,"Error Occured",Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                holder.loveIcon.setImageResource(R.drawable.ic_love2_icon)
                val restDel = DBASyncTask(Context,restaurants,3).execute()
                val checkDel = restDel.get()
                if(checkDel)
                {
                    Toast.makeText(Context,"Removed from Favourites",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(Context,"Error Occured",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    class DBASyncTask(val context:Context,val rest:FavRestaurants,val mode:Int):
        AsyncTask<Void, Void, Boolean>(){
        val db = Room.databaseBuilder(context, RestDatabase::class.java,"rest-db").fallbackToDestructiveMigration().build()
        override fun doInBackground(vararg params: Void?): Boolean {
            when(mode)
            {
                1->{
                    val rest:FavRestaurants? = db.RestDao().searchFav(rest.rest_id.toString())
                    db.close()
                    return rest !=null
                }
                2->{
                    db.RestDao().restInsert(rest)
                    db.close()
                    return true
                }
                3->{
                    db.RestDao().restRemove(rest)
                    db.close()
                    return true
                }
            }
            return false
        }

    }
}