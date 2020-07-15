package com.kobeissidev.elements.element

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kobeissidev.elements.element.model.Item
import com.kobeissidev.elements.element.view.ItemView

internal class ElementAdapter(private val items: List<Item>) :
    RecyclerView.Adapter<ElementAdapter.ElementViewHolder>() {

    private var highlightedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ElementViewHolder(parent.context)

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) = (holder.itemView as ItemView).run {
        bind(items[position])
        setOnClickListener {
            highlightedPosition = position
            notifyDataSetChanged()
        }
        if (highlightedPosition == position) {
            onHighlighted()
        } else {
            onUnHighlighted()
        }
    }

    inner class ElementViewHolder(context: Context) : RecyclerView.ViewHolder(ItemView(context))
}

