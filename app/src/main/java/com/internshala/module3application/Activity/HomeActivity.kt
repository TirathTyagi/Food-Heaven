package com.internshala.module3application.Activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.internshala.module3application.Fragments.DashboardFragment
import com.internshala.module3application.Fragments.FaqFragment
import com.internshala.module3application.Fragments.FavouritesFragment
import com.internshala.module3application.Fragments.ProfileFragment
import com.internshala.module3application.R

class HomeActivity : AppCompatActivity() {
    lateinit var drawerLayout:DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    lateinit var sharedPreferences: SharedPreferences
    lateinit var phone:String
    var previousItem:MenuItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        drawerLayout = findViewById(R.id.drawerLayoutHome)
        coordinatorLayout = findViewById(R.id.coordinatorLayoutHome)
        toolbar = findViewById(R.id.toolbarHomeLayout)
        frameLayout = findViewById(R.id.frameLayoutHome)
        navigationView = findViewById(R.id.navigationViewHome)
        sharedPreferences = getSharedPreferences(R.string.login_preference.toString(), MODE_PRIVATE)
        if(intent!=null)
        {
            phone = intent.getStringExtra("Phone").toString()
            println("we got here: "+phone)
        }
        setUpToolbar()
        val actionBarDrawerToggle = ActionBarDrawerToggle(this@HomeActivity,drawerLayout,R.string.open_drawer,R.string.close_drawer)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        openDashboard()
        navigationView.setNavigationItemSelectedListener {
            if(previousItem!=null)
            {
                previousItem?.isChecked = false
            }
            it.isCheckable=true
            it.isChecked = true
            previousItem = it
            when(it.itemId)
            {
                R.id.home->openDashboard()
                R.id.profile->openProfile()
                R.id.favourite->openFavourites()
                R.id.faq->openFaq()
                R.id.logout->logout()
            }
            return@setNavigationItemSelectedListener true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id ==android.R.id.home)
        {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frameLayoutHome)
        when(frag)
        {
            !is DashboardFragment ->openDashboard()
            else -> super.onBackPressed()
        }
    }
    fun setUpToolbar()
    {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Food's Heaven"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    fun openDashboard()
    {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayoutHome, DashboardFragment()).commit()
        drawerLayout.closeDrawers()
        supportActionBar?.title = "Dashboard"
        navigationView.setCheckedItem(R.id.home)
    }
    fun openFavourites()
    {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayoutHome,FavouritesFragment()).commit()
        drawerLayout.closeDrawers()
        supportActionBar?.title = "Favourites"
        navigationView.setCheckedItem(R.id.favourite)
    }
    fun openProfile()
    {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayoutHome,ProfileFragment()).commit()
        drawerLayout.closeDrawers()
        supportActionBar?.title = "Profile"
        navigationView.setCheckedItem(R.id.profile)
    }
    fun openFaq()
    {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayoutHome,FaqFragment()).commit()
        drawerLayout.closeDrawers()
        supportActionBar?.title = "FAQ"
        navigationView.setCheckedItem(R.id.faq)
    }
    fun logout()
    {
        val intent = Intent(this@HomeActivity,MainActivity::class.java)
        sharedPreferences.edit().clear().apply()
        startActivity(intent)
        finish()
    }
}
