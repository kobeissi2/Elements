package com.kobeissidev.elements.view

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.kobeissidev.elements.R
import com.kobeissidev.elements.model.Element
import com.kobeissidev.elements.util.extension.highlight

/**
 * View for the Element.
 */
class ElementView(context: Context) : LinearLayout(context) {

    private var elementButton: MaterialButton? = null

    init {
        // Inflate the layout.
        View.inflate(context, R.layout.view_element, this)
    }

    /**
     * Bind the element to the button.
     */
    fun bind(element: Element) {
        // Initialize the element button and set the text to the name of the element.
        elementButton = findViewById<MaterialButton>(R.id.element_button).also { it.text = element.name }
    }

    /**
     * Highlight the element button.
     */
    fun onHighlighted() = elementButton?.highlight()

    /**
     * Remove the highlight on the element button.
     */
    fun onUnHighlighted() = elementButton?.let {
        val backgroundColor = ContextCompat.getColor(context, R.color.colorPrimary)
        it.backgroundTintList = ColorStateList.valueOf(backgroundColor)
    }
}