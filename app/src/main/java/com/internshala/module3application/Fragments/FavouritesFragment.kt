package com.internshala.module3application.Fragments

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.internshala.module3application.Adapter.FavouriteAdapter
import com.internshala.module3application.Database.FavRestaurants
import com.internshala.module3application.Database.RestDatabase
import com.internshala.module3application.R
class FavouritesFragment : Fragment() {
    lateinit var recyclerFavourite:RecyclerView
    lateinit var progressFavourite:RelativeLayout
    lateinit var progressBarFav:ProgressBar
    lateinit var layoutManagerFav:RecyclerView.LayoutManager
    lateinit var recyclerAdapter: FavouriteAdapter
    var favList = listOf<FavRestaurants>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)
        recyclerFavourite = view.findViewById(R.id.recyclerFavourite)
        progressFavourite = view.findViewById(R.id.progressLayoutFav)
        progressBarFav = view.findViewById(R.id.progressFavourite)
        layoutManagerFav = GridLayoutManager(activity as Context,2)
        favList = GetFavourites(activity as Context).execute().get()
        progressBarFav.visibility = View.VISIBLE
        progressFavourite.visibility = View.VISIBLE
        if(activity!=null)
        {
            progressFavourite.visibility = View.GONE
            progressBarFav.visibility = View.GONE
            recyclerAdapter = FavouriteAdapter(activity as Context,favList)
            recyclerFavourite.adapter = recyclerAdapter
            recyclerFavourite.layoutManager = layoutManagerFav
        }
        return view
    }
    class GetFavourites(val Context:Context):AsyncTask<Void,Void,List<FavRestaurants>>()
    {
        override fun doInBackground(vararg params: Void?): List<FavRestaurants> {
            val db = Room.databaseBuilder(Context,RestDatabase::class.java,"rest-db").build()
            return db.RestDao().getRests()
        }

    }
}