package com.kobeissidev.elements

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

    private val repository = LastFMRepository()
    private var currentTag: String? = null

    val topTagsList: LiveData<Tag> = repository.topTagsList
    val topTracksList: LiveData<Track> = repository.topTracksList

    val elements = mutableListOf<Element>()
    val currentElement get() = elements.find { it.name == currentTag }

    var selectedElementPosition: Int = -1
        private set
    var selectedItemPosition: Int = -1
        private set

    fun onElementSelected(position: Int) {
        selectedElementPosition = position
    }

    fun onItemSelected(position: Int) {
        selectedItemPosition = position
    }

    fun fetchTopTags() = viewModelScope.launch { repository.getTopTagsAsync() }

    fun fetchTopTracks(tag: String) = viewModelScope.launch {
        currentTag = tag
        repository.getTopTracksAsync(tag)
    }

    fun onReceivedTags(tag: Tag) = elements.run {
        clear()
        tag.topTags.tag.forEach { add(Element(it.name)) }
    }

    fun onReceivedTracks(track: Track) {
        elements.find { it.name == currentTag }?.items = track.tracks.track.map { Item(it.name) }
    }
}