package com.kobeissidev.elements.model

import com.squareup.moshi.Json

/**
 * Track object from LastFM
 */
data class Track(@field:Json(name = "tracks") val tracks: TopTracks)

/**
 * Track object inside the Track object
 * Only the used properties are being de-serialized.
 * @param track contains all of the tracks.
 */
data class TopTracks(@field:Json(name = "track") val track: List<TrackItem>)

/**
 * Track object that contains the information we need
 * Only the used properties are being de-serialized.
 * @param name contains name of the track.
 * @param duration contains duration of the track.
 */
data class TrackItem(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "duration") val duration: Long
)