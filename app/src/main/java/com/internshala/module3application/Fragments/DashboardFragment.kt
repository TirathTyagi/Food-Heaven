package com.internshala.module3application.Fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.internshala.module3application.Adapter.DashboardAdapter
import com.internshala.module3application.Database.FavRestaurants
import com.internshala.module3application.R
import com.internshala.nerdshub.util.ConnectionManager
import org.json.JSONException

class DashboardFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager:RecyclerView.LayoutManager
    lateinit var recyclerAdapter:DashboardAdapter
    val restList = arrayListOf<FavRestaurants>()
    lateinit var progressLayout:RelativeLayout
    lateinit var progressBar: ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard,container,false)
        recyclerView = view.findViewById(R.id.recyclerDashboard)
        layoutManager = LinearLayoutManager(activity)
        progressLayout = view.findViewById(R.id.progressLayout)
        progressBar = view.findViewById(R.id.progressDashboard)
        progressLayout.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE
        setHasOptionsMenu(true)
        val queue = Volley.newRequestQueue(activity as Context)
        val url = "http://13.235.250.119/v2/restaurants/fetch_result/"
       if(ConnectionManager().checkConnectivity(activity as Context)) {
           val jsonObjectRequest =
               object : JsonObjectRequest(Request.Method.GET,url,null,Response.Listener { try{
                progressLayout.visibility = View.GONE
                progressBar.visibility = View.GONE
                val data = it.getJSONObject("data")
                val success = data.getBoolean("success")
                if(success)
                {
                    val dataArray = data.getJSONArray("data")
                    for(i in 0 until dataArray.length()){
                        val restJsonObject = dataArray.getJSONObject(i)
                        val restObject = FavRestaurants(
                            restJsonObject.getInt("id"),
                            restJsonObject.getString("name"),
                            restJsonObject.getString("cost_for_one"),
                            restJsonObject.getString("rating"),
                            restJsonObject.getString("image_url")
                        )
                        restList.add(restObject)
                        recyclerAdapter = DashboardAdapter(activity as Context,restList)
                        recyclerView.adapter = recyclerAdapter
                        recyclerView.layoutManager = layoutManager
                    }
                }
                else
                {
                    Toast.makeText(activity,"No Internet Connection",Toast.LENGTH_SHORT).show()
                }
            }
            catch(e:JSONException)
            {
                Toast.makeText(activity,"Unexpected Error",Toast.LENGTH_SHORT).show()
            }
           },
               Response.ErrorListener {
                   it.printStackTrace()
               }){
               override fun getHeaders(): MutableMap<String, String> {
                   val headers = HashMap<String, String>()
                   headers["Content-type"] = "application/json"
                   headers["token"] = "330ce002cfe617"
                   return headers
               }
           }
           queue.add(jsonObjectRequest)
       }
        else
       {
           val dialog = AlertDialog.Builder(activity as Context)
           dialog.setTitle("ERROR!!!")
           dialog.setMessage("Not Connected to the Internet.")
           dialog.setPositiveButton("Fix") { text, listener ->
               val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
               startActivity(settingsIntent)
               activity?.finish() // So that the app is reopened and list is refreshed
           }
           dialog.setNegativeButton("Exit") { text, listener ->
               ActivityCompat.finishAffinity(activity as Activity) // This closes the app.
           }
           dialog.create()
           dialog.show()
       }
        return view
    }

}