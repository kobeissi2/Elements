package com.kobeissidev.elements.model

import com.squareup.moshi.Json

data class Track(@field:Json(name = "tracks") val tracks: TopTracks)

data class TopTracks(@field:Json(name = "track") val track: List<TrackItem>)

data class TrackItem(@field:Json(name = "name") val name: String)