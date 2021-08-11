package com.kobeissidev.elements.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

/**
 * Tag object from LastFM
 */
@Parcelize
@JsonClass(generateAdapter = true)
data class Tag(@field:Json(name = "toptags") val topTags: TopTags) : Parcelable

/**
 * Tag object inside the Tag object
 * Only the used properties are being de-serialized.
 * @param tag contains all of the tags.
 */
@Parcelize
@JsonClass(generateAdapter = true)
data class TopTags(@field:Json(name = "tag") val tag: List<TagItem>) : Parcelable

/**
 * Tag object that contains the information we need
 * Only the used properties are being de-serialized.
 * @param name contains name of the tag.
 */
@Parcelize
@JsonClass(generateAdapter = true)
data class TagItem(@field:Json(name = "name") val name: String) : Parcelable