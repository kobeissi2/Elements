package com.kobeissidev.elements.repository

import androidx.lifecycle.MutableLiveData
import com.kobeissidev.elements.model.Tag
import com.kobeissidev.elements.model.Track
import com.kobeissidev.elements.network.NetworkInteractor

class LastFMRepository {

    val topTagsList = MutableLiveData<Tag>()
    val topTracksList = MutableLiveData<Track>()

    private val networkInteractor = NetworkInteractor()

    suspend fun getTopTagsAsync() = networkInteractor.getTopTagsAsync().run {
        if (isSuccessful) {
            topTagsList.postValue(body)
        }
    }

    suspend fun getTopTracksAsync(tag: String) = networkInteractor.getTopTracksAsync(tag).run {
        if (isSuccessful) {
            topTracksList.postValue(body)
        }
    }
}