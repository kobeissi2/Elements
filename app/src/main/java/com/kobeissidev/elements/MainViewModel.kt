package com.kobeissidev.elements

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kobeissidev.elements.model.Element
import com.kobeissidev.elements.model.Item
import com.kobeissidev.elements.model.Tag
import com.kobeissidev.elements.model.Track
import com.kobeissidev.elements.repository.LastFMRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    /**
     * Instance of the repository
     */
    private val repository = LastFMRepository()

    /**
     * Current selected tag so we know which tracks to fetch.
     */
    private var currentTag: String? = null

    /**
     * Wrapper for the repository tag LiveData which removes the mutability.
     */
    val topTagsList: LiveData<Tag> = repository.topTagsList

    /**
     * Wrapper for the repository track LiveData which removes the mutability.
     */
    val topTracksList: LiveData<Track> = repository.topTracksList

    /**
     * Keep track of all of the elements
     */
    val elements = mutableListOf<Element>()

    /**
     * Get the instance of the current element using the current tag.
     */
    val currentElement get() = elements.find { it.name == currentTag }

    /**
     * Cache the selected element position so we can retain it on orientation change.
     */
    var selectedElementPosition: Int = -1
        private set

    /**
     * Cache the selected item position so we can retain it on orientation change.
     */
    var selectedItemPosition: Int = -1
        private set

    /**
     * Cache the current progress so we can retain it on orientation change.
     */
    var currentProgress: Int = 0
        private set

    /**
     * Updated the selected element position.
     */
    fun onElementSelected(position: Int) {
        selectedElementPosition = position
    }

    /**
     * Updated the selected item position.
     */
    fun onItemSelected(position: Int) {
        selectedItemPosition = position
    }

    /**
     * Fetch the top tags using the viewModelScope.
     */
    fun fetchTopTags() = viewModelScope.launch { repository.getTopTagsAsync() }

    /**
     * Fetch the top tracks using the viewModelScope.
     */
    fun fetchTopTracks(tag: String) = viewModelScope.launch {
        currentTag = tag
        repository.getTopTracksAsync(tag)
    }

    /**
     * Clears all elements and saves the ones received.
     */
    fun onReceivedTags(tag: Tag) = elements.run {
        clear()
        tag.topTags.tag.forEach { add(Element(it.name)) }
    }

    /**
     * Update the current element's items with the tracks received.
     */
    fun onReceivedTracks(track: Track) {
        currentElement!!.items = track.tracks.track.map { Item(it.name, it.duration) }
    }

    /**
     * Updated the current progress.
     */
    fun onProgressChanged(progress: Int?) {
        currentProgress = progress ?: 0
    }
}