package com.kobeissidev.elements.model

data class Element(
    val name: String,
    var items: List<Item>? = null
)