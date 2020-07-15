package com.kobeissidev.elements.view

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.kobeissidev.elements.R
import com.kobeissidev.elements.element.model.Item

class ItemView(context: Context) : LinearLayout(context) {

    private var itemButton: MaterialButton? = null

    init {
        View.inflate(context, R.layout.view_item, this)
    }

    fun bind(item: Item)  {
        itemButton = findViewById<MaterialButton>(R.id.item_button).also { it.text = item.name }
    }

    fun onHighlighted() {
        val colorHighlight = ContextCompat.getColor(context, R.color.colorHighlight)
        itemButton?.backgroundTintList = ColorStateList.valueOf(colorHighlight)
    }

    fun onUnHighlighted() {
        val colorUnHighlight = ContextCompat.getColor(context, R.color.colorUnHighlight)
        itemButton?.backgroundTintList = ColorStateList.valueOf(colorUnHighlight)
    }
}