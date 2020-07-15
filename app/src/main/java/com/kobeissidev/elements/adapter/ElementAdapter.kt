package com.kobeissidev.elements.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kobeissidev.elements.model.Element
import com.kobeissidev.elements.view.ElementView

internal class ElementAdapter(private val elements: List<Element>, private val listener: ElementListener, selectedPosition: Int) :
    RecyclerView.Adapter<ElementAdapter.ElementViewHolder>() {

    /**
     * Keeps track of highlighted positions.
     */
    private var highlightedPosition = selectedPosition

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ElementViewHolder(parent.context)

    override fun getItemCount() = elements.size

    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        // Change the scope inside the block to the item view so we can use it's properties without making another variable.
        (holder.itemView as ElementView).run {
            val element = elements[position]
            //Bind the button inside the view to the name of the element.
            bind(element)
            // Listen for clicks and highlight the view.
            setOnClickListener {
                // Keep track so every other view can be unhighlighted.
                highlightedPosition = position
                // Inform the adapter so the UI gets refreshed.
                notifyDataSetChanged()
            }
            // Once it rebinds, highlight if the position matches.
            if (highlightedPosition == position) {
                onHighlighted()
                // Also inform the listener of the changed element so we can fetch the associated items.
                listener.onElementSelected(element, position)
            } else {
                // Otherwise, remove the highlight.
                onUnHighlighted()
            }
        }
    }

    /**
     * ViewHolder for this adapter.
     * It doesn't need to be exposed since it is only used by the adapter.
     */
    internal inner class ElementViewHolder(context: Context) : RecyclerView.ViewHolder(ElementView(context))

    /**
     * Interface to inform the listeners of changes.
     */
    interface ElementListener {
        fun onElementSelected(element: Element, position: Int)
    }
}

