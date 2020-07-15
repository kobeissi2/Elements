package com.kobeissidev.elements.view

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import com.google.android.material.button.MaterialButton
import com.kobeissidev.elements.R
import com.kobeissidev.elements.model.Element
import com.kobeissidev.elements.util.extension.highlight
import com.kobeissidev.elements.util.extension.unHighlight

class ElementView(context: Context) : LinearLayout(context) {

    private var itemButton: MaterialButton? = null

    init {
        View.inflate(context, R.layout.view_item, this)
    }

    fun bind(element: Element)  {
        itemButton = findViewById<MaterialButton>(R.id.item_button).also { it.text = element.name }
    }

    fun onHighlighted() = itemButton?.highlight()

    fun onUnHighlighted() = itemButton?.unHighlight()
}