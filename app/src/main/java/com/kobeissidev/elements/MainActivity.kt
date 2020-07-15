package com.kobeissidev.elements

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textview.MaterialTextView
import com.kobeissidev.elements.adapter.ElementAdapter
import com.kobeissidev.elements.adapter.ItemAdapter
import com.kobeissidev.elements.element.model.Element
import com.kobeissidev.elements.element.model.Item

class MainActivity : AppCompatActivity(), ElementAdapter.ElementListener, ItemAdapter.ItemListener {

    private lateinit var toolbar: MaterialToolbar

    private var drawerLayout: DrawerLayout? = null
    private var toggle: ActionBarDrawerToggle? = null
    private var leftRecyclerView: RecyclerView? = null
    private var rightRecyclerView: RecyclerView? = null

    private val isPortrait get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    //TODO: Add elements
    private val elements = listOf(
        Element("Test", listOf(Item("Item"))),
        Element("Test2", listOf(Item("Item"), Item("Item2")))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById<MaterialToolbar>(R.id.main_toolbar).also { setSupportActionBar(it) }
        rightRecyclerView = findViewById(R.id.main_right_recycler_view)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(isPortrait)
            it.setHomeButtonEnabled(isPortrait)
        }

        drawerLayout = findViewById(R.id.main_drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)
        drawerLayout?.addDrawerListener(toggle!!)

        leftRecyclerView = findViewById<RecyclerView>(R.id.main_left_recycler_view).also {
            it.adapter = ElementAdapter(elements, this, viewModel.selectedElementPosition)
        }
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
        if (isPortrait) {
            toggle?.syncState()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (isPortrait) {
            toggle?.onConfigurationChanged(newConfig)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        if (isPortrait && toggle?.onOptionsItemSelected(item) == true) {
            true
        } else {
            super.onOptionsItemSelected(item)
        }

    override fun onElementSelected(element: Element, position: Int) {
        viewModel.onElementSelected(position)
        toolbar.title = element.name
        rightRecyclerView?.let {
            it.adapter = ItemAdapter(element.items, this, viewModel.selectedItemPosition)
            it.smoothScrollToPosition(0)
        }
        drawerLayout?.closeDrawer(GravityCompat.START)
    }

    override fun onItemSelected(position: Int) = viewModel.onItemSelected(position)
}