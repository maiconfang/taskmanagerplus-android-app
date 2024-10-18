package maif.taskmanagerplus

import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import maif.taskmanagerplus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // AppBarConfiguration stores the top-level destinations for the navigation
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Using View Binding to inflate the layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setting up the toolbar for this activity
        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // AppBarConfiguration defines which fragments/activities are considered top-level
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_task
            ), drawerLayout
        )

        // Setting up ActionBar with the NavigationController
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Connecting NavigationView with the NavigationController
        navView.setupWithNavController(navController)

        // Get the user email passed from the login screen
        val userEmail = intent.getStringExtra("USER_EMAIL")

        // Access the header view and update the email TextView
        val headerView = navView.getHeaderView(0)
        val navHeaderSubtitle = headerView.findViewById<TextView>(R.id.textView) // ID of the TextView in the header
        navHeaderSubtitle.text = userEmail  // Set the user's email in the header
    }

    // Inflating the options menu (main menu)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    // Handling Up navigation when using the ActionBar
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
