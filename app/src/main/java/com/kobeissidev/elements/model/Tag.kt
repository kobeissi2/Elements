package com.kobeissidev.elements.model

import com.squareup.moshi.Json

data class Tag(@field:Json(name = "toptags") val topTags: TopTags)

data class TopTags(@field:Json(name = "@attr") val attr: Attributes, @field:Json(name = "tag") val tag: List<TagItem>)

data class Attributes(
    @field:Json(name = "offset") val offset: Int,
    @field:Json(name = "num_res") val numRes: Int,
    @field:Json(name = "total") val total: Int
)

data class TagItem(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "count") val count: String,
    @field:Json(name = "reach") val reach: String
)