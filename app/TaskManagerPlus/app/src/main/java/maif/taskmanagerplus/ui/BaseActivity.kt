package maif.taskmanagerplus.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import maif.taskmanagerplus.R
import maif.taskmanagerplus.ui.dashboard.TaskDashboardActivity
import maif.taskmanagerplus.ui.fragment.TaskOverviewFragment
import maif.taskmanagerplus.ui.gallery.GalleryFragment
import maif.taskmanagerplus.ui.home.HomeFragment

open class BaseActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base) // Use o layout comum

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)

        setupDrawerContent(navigationView)
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            selectDrawerItem(menuItem)
            true
        }
    }

    private fun selectDrawerItem(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.nav_home -> {
                replaceFragment(HomeFragment())
            }
            R.id.nav_gallery -> {
                // Navegar para GalleryActivity ou trocar o fragmento
                replaceFragment(GalleryFragment())
            }
            R.id.nav_task_dashboard -> {
                // Navegar para TaskDashboardFragment ou Activity
                startActivity(Intent(this, TaskDashboardActivity::class.java))
            }
            // Other itens here
            R.id.nav_overview_ask -> {
                replaceFragment(TaskOverviewFragment())
            }
        }
        menuItem.isChecked = true
        drawerLayout.closeDrawers()
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_frame, fragment)
            .commit()
    }
}
