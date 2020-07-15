package com.kobeissidev.elements.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kobeissidev.elements.element.model.Element
import com.kobeissidev.elements.element.model.Item
import com.kobeissidev.elements.view.ItemView

internal class ItemAdapter(private val items: List<Item>, private val listener: ItemListener, selectedPosition: Int) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var highlightedPosition = selectedPosition

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
            listener.onItemSelected(position)
        } else {
            onUnHighlighted()
        }
    }

    inner class ItemViewHolder(context: Context) : RecyclerView.ViewHolder(ItemView(context))

    interface ItemListener {
        fun onItemSelected(position: Int)
    }
}

