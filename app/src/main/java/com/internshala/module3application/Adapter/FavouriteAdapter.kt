package com.internshala.module3application.Adapter

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.internshala.module3application.Database.FavRestaurants
import com.internshala.module3application.R
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class FavouriteAdapter(val Context: Context, val favList:List<FavRestaurants>) :RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>(){
    class FavouriteViewHolder(view: View):RecyclerView.ViewHolder(view){
        val favRestName:TextView = view.findViewById(R.id.txtFavRest)
        val favRestPrice:TextView = view.findViewById(R.id.txtPricePersonFav)
        val favRestRating:TextView = view.findViewById(R.id.txtFavRating)
        val favRestImg: ImageView = view.findViewById(R.id.imgIconRest)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_favourite,parent,false)
        return FavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val rest = favList[position]
        holder.favRestName.text = rest.restName
        holder.favRestPrice.text = rest.restPrice
        holder.favRestRating.text = rest.restRating
        Picasso.get().load(rest.restImg).error(R.drawable.ic_default_rest).into(holder.favRestImg)
    }

    override fun getItemCount(): Int {
        return favList.size
    }
}