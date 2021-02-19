package com.example.instagramclonefirebase

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_feed.*

class MainActivity2 : AppCompatActivity() {

    private  var auth = FirebaseAuth.getInstance()


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuinflater = menuInflater
        menuinflater.inflate(R.menu.logout_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout -> {
                auth.signOut()

                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
                true
            }

            R.id.upload ->{
                val intent = Intent(this,UploadActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)




        val bottomnavView : BottomNavigationView = findViewById(R.id.bottomview)
        val navController = findNavController(R.id.navhome)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.feedFragment,R.id.profileFragment
        ))
        setupActionBarWithNavController(navController,appBarConfiguration)
        bottomnavView.setupWithNavController(navController)

    }
}



/*
*   val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
* */