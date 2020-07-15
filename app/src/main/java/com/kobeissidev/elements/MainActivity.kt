package com.kobeissidev.elements

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textview.MaterialTextView
import com.kobeissidev.elements.element.ElementAdapter
import com.kobeissidev.elements.element.model.Element
import com.kobeissidev.elements.element.model.Item

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: MaterialToolbar
    private lateinit var toggle: ActionBarDrawerToggle

    //TODO: Add elements
    private val elements = listOf(
        Element("Test", listOf(Item("Item"))),
        Element("Test2", listOf(Item("Item"), Item("Item2")))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById<MaterialToolbar>(R.id.main_toolbar).also { setSupportActionBar(it) }
        drawerLayout = findViewById<DrawerLayout>(R.id.main_drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)

        drawerLayout.addDrawerListener(toggle)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
        }

        setUpNavigationView()
    }

    override fun onBackPressed() = if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
        drawerLayout.closeDrawer(GravityCompat.START)
    } else {
        super.onBackPressed()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem) = if (toggle.onOptionsItemSelected(item)) {
        true
    } else {
        super.onOptionsItemSelected(item)
    }

    private fun setUpNavigationView() =
        findViewById<NavigationView>(R.id.main_navigation_view).run {
            // Add new submenu for the navigation view
            menu.addSubMenu(getString(R.string.sub_menu)).also {
                // Loop through all of the elements and add them to the menu
                elements.forEach { element -> it.add(element.name) }
            }
            setNavigationItemSelectedListener {
                toolbar.title = it.title
                handleNavigationItemSelected(it)
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
            // Redraw the navigation view
            invalidate()
        }

    private fun handleNavigationItemSelected(menuItem: MenuItem) {
        elements.find { it.name == menuItem.title }?.let {
            findViewById<RecyclerView>(R.id.main_recycler_view).adapter = ElementAdapter(it.items)
        }
    }
}