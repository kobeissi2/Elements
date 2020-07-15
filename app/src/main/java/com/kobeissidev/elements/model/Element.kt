package com.kobeissidev.elements.model

/**
 * Element object
 * @param name will be displayed on the UI
 * @param items contains the associated items.
 */
data class Element(
    val name: String,
    var items: List<Item>? = null
)