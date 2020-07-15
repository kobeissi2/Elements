package com.kobeissidev.elements.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kobeissidev.elements.util.extension.result
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Interactor with the LastFM API.
 */
class LastFMNetworkInteractor {

    private companion object {
        const val BASE_URL = "https://ws.audioscrobbler.com/"
    }

    private val client = OkHttpClient().newBuilder().build()

    /**
     * Build the retrofit instance using an OkHttpClient.
     */
    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    /**
     * Create API from Web Service.
     */
    private val lastFMAPI = retrofit.create(LastFMWebService::class.java)

    /**
     * Fetch the top tags and convert to the Result object.
     */
    suspend fun getTopTagsAsync() = lastFMAPI.getTopTagsAsync().await().result

    /**
     * Fetch the top tracks and convert to the Result object.
     */
    suspend fun getTopTracksAsync(tag: String) = lastFMAPI.getTopTracksAsync(tag).await().result
}