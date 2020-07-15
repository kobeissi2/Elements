package com.kobeissidev.elements.model

import com.squareup.moshi.Json

/**
 * Tag object from LastFM
 */
data class Tag(@field:Json(name = "toptags") val topTags: TopTags)

/**
 * Tag object inside the Tag object
 * Only the used properties are being de-serialized.
 * @param tag contains all of the tags.
 */
data class TopTags(@field:Json(name = "tag") val tag: List<TagItem>)

/**
 * Tag object that contains the information we need
 * Only the used properties are being de-serialized.
 * @param name contains name of the tag.
 */
data class TagItem(@field:Json(name = "name") val name: String)