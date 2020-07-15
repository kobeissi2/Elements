package com.kobeissidev.elements.network

import com.kobeissidev.elements.model.Tag
import com.kobeissidev.elements.model.Track
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFMWebService {

    private companion object {
        const val API_KEY = "49e20e8b8632e0dbb305464b4f784f61"
    }

    @GET("/2.0/?method=tag.getTopTags&api_key=$API_KEY&format=json")
    fun getTopTagsAsync(): Deferred<Response<Tag>>

    @GET("/2.0/?method=tag.gettoptracks&api_key=$API_KEY&format=json")
    fun getTopTracksAsync(@Query("tag") tag: String): Deferred<Response<Track>>
}