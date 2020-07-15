package com.kobeissidev.elements

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textview.MaterialTextView
import com.kobeissidev.elements.adapter.ElementAdapter
import com.kobeissidev.elements.adapter.ItemAdapter
import com.kobeissidev.elements.model.Element

class MainActivity : AppCompatActivity(), ElementAdapter.ElementListener, ItemAdapter.ItemAdapterListener {

    /**
     * Views being used by the activity.
     */
    private var toolbar: MaterialToolbar? = null
    private var drawerLayout: DrawerLayout? = null
    private var toggle: ActionBarDrawerToggle? = null
    private var leftRecyclerView: RecyclerView? = null
    private var rightRecyclerView: RecyclerView? = null

    /**
     * Returns true if the current configuration is portrait mode
     */
    private val isPortrait get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    /**
     * Instance of the viewModel.
     */
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Fetch the tags.
        viewModel.fetchTopTags()
        // Instantiate the toolbar variable and set the action bar.
        toolbar = findViewById<MaterialToolbar>(R.id.main_toolbar).also { setSupportActionBar(it) }
        // Instantiate the other views.
        drawerLayout = findViewById(R.id.main_drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)
        // Add the toggle as a listener
        drawerLayout?.addDrawerListener(toggle!!)
        // Show the hamburger icon only if it is portrait mode.
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(isPortrait)
            it.setHomeButtonEnabled(isPortrait)
        }
        // Observe the tag list LiveData
        viewModel.topTagsList.observe(this, Observer { tags ->
            // Convert the tags to Element object.
            viewModel.onReceivedTags(tags)
            // Instantiate the left RecyclerView. In portrait mode, it is inside the hamburger menu.
            leftRecyclerView = findViewById<RecyclerView>(R.id.main_left_recycler_view).also {
                // Also add the adapter using the new elements we just created.
                // Pass the position in case we need to highlight it.
                it.adapter = ElementAdapter(viewModel.elements, this, viewModel.selectedElementPosition)
            }
        })
        // Observe the track list LiveData
        viewModel.topTracksList.observe(this, Observer { tracks ->
            // Add the tracks as Item to the current Element object.
            viewModel.onReceivedTracks(tracks)
            // Instantiate the right RecyclerView. In portrait mode, it is outside of the hamburger menu.
            rightRecyclerView = findViewById<RecyclerView>(R.id.main_right_recycler_view).also {
                // Also add the adapter using the new items inside our element we just created.
                // Pass the position in case we need to highlight it.
                it.adapter = ItemAdapter(viewModel.currentElement!!.items!!, this, viewModel.selectedItemPosition,
                    viewModel.currentProgress)
                // Scroll to the top.
                it.smoothScrollToPosition(0)
            }
        })
    }

    /**
     * If the drawerLayout is open, close it before close the app.
     */
    override fun onBackPressed() {
        if (drawerLayout?.isDrawerOpen(GravityCompat.START) == true) {
            drawerLayout?.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /**
     * If in portrait mode, sync the toggle state with the drawerLayout.
     */
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (isPortrait) {
            toggle?.syncState()
        }
    }

    /**
     * If in portrait mode, pass any configuration changes to the toggle.
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (isPortrait) {
            toggle?.onConfigurationChanged(newConfig)
        }
    }

    /**
     * If in portrait mode and an option is selected, pass any option item selected to the toggle.
     */
    override fun onOptionsItemSelected(item: MenuItem) =
        if (isPortrait && toggle?.onOptionsItemSelected(item) == true) {
            true
        } else {
            super.onOptionsItemSelected(item)
        }

    /**
     * Handle the element being selected in the left RecyclerView.
     */
    override fun onElementSelected(element: Element, position: Int) {
        // Prevent clicking the same element logic.
        if (position != viewModel.selectedElementPosition) {
            // Cache the position of the selected element.
            viewModel.onElementSelected(position)
            // Clear the position of any selected item.
            viewModel.onItemSelected(-1)
            // Fetch the tracks for the current element.
            viewModel.fetchTopTracks(element.name)
        }
        // Update the toolbar with the name of the Element.
        findViewById<MaterialTextView>(R.id.main_toolbar_text_view).text = element.name.capitalize()
        // Close the drawer if it is initialized.
        drawerLayout?.closeDrawer(GravityCompat.START)
    }

    /**
     * Handle the element being selected in the right RecyclerView.
     * Caches the position of the select item.
     */
    override fun onItemSelected(position: Int) = viewModel.onItemSelected(position)

    /**
     * Handle the progress changing.
     * Caches the progress.
     */
    override fun onProgressChanged(progress: Int?) = viewModel.onProgressChanged(progress)
}