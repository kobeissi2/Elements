package com.kobeissidev.elements.model

/**
 * Item object
 * @param name will be displayed on the UI
 * @param duration will be used to keep the progress going to mimic a song playing.
 */
data class Item(val name: String, val duration: Long)