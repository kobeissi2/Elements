package com.kobeissidev.elements.util.extension

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import com.kobeissidev.elements.R
import com.kobeissidev.elements.model.Result
import retrofit2.Response

/**
 * Highlights the view using the colorHighlight specified in the colors.xml file.
 */
fun View.highlight() {
    val colorHighlight = ContextCompat.getColor(context, R.color.colorHighlight)
    backgroundTintList = ColorStateList.valueOf(colorHighlight)
}

/**
 * Converts a Response object to a result one to make getting the data a bit easier.
 */
val <T> Response<T>.result get() = Result(isSuccessful, body(), code())