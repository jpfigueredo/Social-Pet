package vortex.project.unify.Views.Activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import vortex.project.unify.R

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration : AppBarConfiguration
    private lateinit var listener: NavController.OnDestinationChangedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.display_fragments) as NavHostFragment? ?: return

        val navController = host.navController

        setupBottomNavMenu(navController)
        setupDrawerNavMenu(navController)

        appBarConfiguration = AppBarConfiguration(navController.graph)

//        appBarConfiguration = AppBarConfiguration(
//                setOf(
//                        R.id.home_dest,
//                        R.id.profile_dest
//                )
//        )

        setupActionBar(navController, appBarConfiguration)

        listener = NavController.OnDestinationChangedListener {controller, destination, arguments ->
            if (destination.id == R.id.home_dest){
                supportActionBar!!.title = "Posts"
            } else if (destination.id == R.id.profile_dest){
                supportActionBar!!.title = "Profile"
            }
        }
    }

    fun setupDrawerMenu(view: View) {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

    private fun setupActionBar(navController: NavController, appBarConfig : AppBarConfiguration) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.display_fragments).navigateUp(appBarConfiguration)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
    }
    private fun setupDrawerNavMenu(navController: NavController) {
        val drawerNav = findViewById<NavigationView>(R.id.drawer_nav_view)
        drawerNav?.setupWithNavController(navController)
    }
}