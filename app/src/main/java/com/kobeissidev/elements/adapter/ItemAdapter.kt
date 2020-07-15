package com.kobeissidev.elements.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kobeissidev.elements.element.model.Item
import com.kobeissidev.elements.view.ItemView

internal class ItemAdapter(private val items: List<Item>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var highlightedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(parent.context)

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = (holder.itemView as ItemView).run {
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

    inner class ItemViewHolder(context: Context) : RecyclerView.ViewHolder(ItemView(context))
}

