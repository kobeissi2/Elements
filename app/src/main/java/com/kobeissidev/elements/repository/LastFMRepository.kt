package com.kobeissidev.elements.repository

import androidx.lifecycle.MutableLiveData
import com.kobeissidev.elements.model.Tag
import com.kobeissidev.elements.model.Track
import com.kobeissidev.elements.network.LastFMNetworkInteractor

/**
 * Repository for storing LastFM related data.
 */
class LastFMRepository {

    /**
     * Interactor to help make the network calls to LastFM.
     */
    private val lastFMNetworkInteractor = LastFMNetworkInteractor()

    /**
     * LiveData for the top tags from LastFM.
     */
    val topTagsList = MutableLiveData<Tag>()

    /**
     * LiveData for the top tracks from LastFM.
     */
    val topTracksList = MutableLiveData<Track>()

    /**
     * Fetches the top tags asynchronously and informs the LiveData of the result if successful.
     */
    suspend fun getTopTagsAsync() = lastFMNetworkInteractor.getTopTagsAsync().run {
        if (isSuccessful) {
            topTagsList.postValue(body)
        }
    }

    /**
     * Fetches the top tracks asynchronously and informs the LiveData of the result if successful.
     */
    suspend fun getTopTracksAsync(tag: String) = lastFMNetworkInteractor.getTopTracksAsync(tag).run {
        if (isSuccessful) {
            topTracksList.postValue(body)
        }
    }
}