package com.kobeissidev.elements.view

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.kobeissidev.elements.R
import com.kobeissidev.elements.model.Item
import com.kobeissidev.elements.util.extension.highlight

/**
 * View for the Item.
 */
class ItemView(context: Context) : LinearLayout(context) {

    private var itemButton: MaterialButton? = null

    init {
        // Inflate the layout.
        View.inflate(context, R.layout.view_item, this)
    }

    /**
     * Bind the element to the button.
     */
    fun bind(item: Item) {
        // Initialize the item button and set the text to the name of the item.
        itemButton = findViewById<MaterialButton>(R.id.item_button).also { it.text = item.name }
    }

    /**
     * Highlight the element button.
     */
    fun onHighlighted() = itemButton?.highlight()

    /**
     * Remove the highlight on the element button.
     */
    fun onUnHighlighted() = itemButton?.let {
        val colorUnHighlight = ContextCompat.getColor(context, R.color.colorUnHighlight)
        it.backgroundTintList = ColorStateList.valueOf(colorUnHighlight)
    }
}