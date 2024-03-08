package com.example.dogsandddapters

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp


class MainActivity : AppCompatActivity() {

    private var navController: NavController? = null
    private val bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)


        val navHostFragment: NavHostFragment? = supportFragmentManager.findFragmentById(R.id.navHostMain) as? NavHostFragment
        navController = navHostFragment?.navController
        navController?.let { NavigationUI.setupActionBarWithNavController(this, it) }

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.mainActivityBottomNavigationView)
        navController?.let { NavigationUI.setupWithNavController(bottomNavigationView, it) }

    }

    private var isAddMenuItemVisible = true

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        val addMenuItem = menu?.findItem(R.id.addPersonPostFragment)
        addMenuItem?.isVisible = isAddMenuItemVisible

        return true
    }

    fun setAddMenuItemVisibility(isVisible: Boolean) {
        isAddMenuItemVisible = isVisible
        invalidateOptionsMenu()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                navController?.navigateUp()
                true
            }
            else -> navController?.let { NavigationUI.onNavDestinationSelected(item, it) } ?: super.onOptionsItemSelected(item)
        }
    }

    fun setBottomBarVisibility(isVisible: Boolean) {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.mainActivityBottomNavigationView)

        if (isVisible) {
            bottomNavigationView.visibility = View.VISIBLE
        } else {
            bottomNavigationView.visibility = View.GONE
        }
    }




}


