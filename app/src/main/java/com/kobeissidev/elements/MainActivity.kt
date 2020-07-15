package com.kobeissidev.elements

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.kobeissidev.elements.adapter.ElementAdapter
import com.kobeissidev.elements.adapter.ItemAdapter
import com.kobeissidev.elements.element.model.Element
import com.kobeissidev.elements.element.model.Item

class MainActivity : AppCompatActivity(), ElementAdapter.ElementListener {

    private lateinit var toolbar: MaterialToolbar
    private var drawerLayout: DrawerLayout? = null
    private var toggle: ActionBarDrawerToggle? = null
    private var leftRecyclerView: RecyclerView? = null
    private var rightRecyclerView: RecyclerView? = null

    //TODO: Add elements
    private val elements = listOf(
        Element("Test", listOf(Item("Item"))),
        Element("Test2", listOf(Item("Item"), Item("Item2")))
    )

    private val isPortrait get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    private val isLandscape get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById<MaterialToolbar>(R.id.main_toolbar).also { setSupportActionBar(it) }
        rightRecyclerView = findViewById(R.id.main_right_recycler_view)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(isPortrait)
            it.setHomeButtonEnabled(isPortrait)
        }

        setUpPortrait()
        setUpLandscape()
    }

    override fun onBackPressed() {
        if (drawerLayout?.isDrawerOpen(GravityCompat.START) == true) {
            drawerLayout?.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle?.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle?.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem) = if (toggle?.onOptionsItemSelected(item) == true) {
        true
    } else {
        super.onOptionsItemSelected(item)
    }

    override fun onElementSelected(element: Element) {
        toolbar.title = element.name
        rightRecyclerView?.adapter = ItemAdapter(element.items)
    }

    private fun setUpPortrait() {
        if (isPortrait) {
            drawerLayout = findViewById(R.id.main_drawer_layout)
            toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)
            drawerLayout?.addDrawerListener(toggle!!)

            findViewById<NavigationView>(R.id.main_navigation_view).run {
                // Add new submenu for the navigation view
                menu.addSubMenu(getString(R.string.sub_menu)).also {
                    // Loop through all of the elements and add them to the menu
                    elements.forEach { element -> it.add(element.name) }
                }
                setNavigationItemSelectedListener { menuItem ->
                    toolbar.title = menuItem.title
                    elements.find { it.name == menuItem.title }?.let { rightRecyclerView?.adapter = ItemAdapter(it.items) }
                    drawerLayout!!.closeDrawer(GravityCompat.START)
                    true
                }
                // Redraw the navigation view
                invalidate()
            }

        }
    }

    private fun setUpLandscape() {
        if (isLandscape) {
            leftRecyclerView = findViewById<RecyclerView>(R.id.main_left_recycler_view).also {
                it.adapter = ElementAdapter(elements, this)
            }
        }
    }
}