package com.kobeissidev.elements.util.extension

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import com.kobeissidev.elements.R

fun View.highlight() {
    val colorHighlight = ContextCompat.getColor(context, R.color.colorHighlight)
    backgroundTintList = ColorStateList.valueOf(colorHighlight)
}

fun View.unHighlight() {
    val colorUnHighlight = ContextCompat.getColor(context, R.color.colorUnHighlight)
    backgroundTintList = ColorStateList.valueOf(colorUnHighlight)
}