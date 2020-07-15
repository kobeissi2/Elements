package com.kobeissidev.elements.network

import com.kobeissidev.elements.util.extension.result


class NetworkInteractor {
    suspend fun getTopTagsAsync() = lastFMAPI.getTopTagsAsync().await().result
    suspend fun getTopTracksAsync(tag: String) = lastFMAPI.getTopTracksAsync(tag).await().result
}