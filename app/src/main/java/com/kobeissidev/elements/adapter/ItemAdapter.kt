package com.kobeissidev.elements.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kobeissidev.elements.model.Item
import com.kobeissidev.elements.view.ItemView

internal class ItemAdapter(
    private val items: List<Item>,
    private val listener: ItemAdapterListener,
    selectedPosition: Int,
    private val currentProgress: Int
) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    /**
     * Keeps track of highlighted positions.
     */
    private var highlightedPosition = selectedPosition

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(parent.context)

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        // Change the scope inside the block to the item view so we can use it's properties without making another variable.
        (holder.itemView as ItemView).run {
            //Bind the button inside the view to the name of the element.
            bind(items[position], currentProgress)
            // Listen for clicks and highlight the view.
            setOnClickListener {
                // Keep track so every other view can be unhighlighted.
                highlightedPosition = position
                // Inform the adapter so the UI gets refreshed.
                notifyDataSetChanged()
            }
            // Once it rebinds, highlight if the position matches.
            if (highlightedPosition == position) {
                onClicked()
                // Also inform the listener of the changed element so we can fetch the associated items.
                listener.onItemSelected(position)
            } else {
                // Otherwise, remove the highlight.
                onUnClicked()
            }
        }
    }

    /**
     * ViewHolder for this adapter.
     * It doesn't need to be exposed since it is only used by the adapter.
     */
    internal inner class ItemViewHolder(context: Context) : RecyclerView.ViewHolder(ItemView(context, listener))

    /**
     * Interface to inform the listeners of changes.
     */
    interface ItemAdapterListener : ItemView.ItemListener {
        fun onItemSelected(position: Int)
    }
}

