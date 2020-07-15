package com.kobeissidev.elements.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kobeissidev.elements.model.Element
import com.kobeissidev.elements.view.ElementView

internal class ElementAdapter(
    private val elements: List<Element>,
    private val listener: ElementListener,
    selectedPosition: Int
) :
    RecyclerView.Adapter<ElementAdapter.ElementViewHolder>() {

    private var highlightedPosition = selectedPosition

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ElementViewHolder(parent.context)

    override fun getItemCount() = elements.size

    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        (holder.itemView as ElementView).run {
            val element = elements[position]
            bind(element)
            setOnClickListener {
                highlightedPosition = position
                notifyDataSetChanged()
            }
            if (highlightedPosition == position) {
                onHighlighted()
                listener.onElementSelected(element, position)
            } else {
                onUnHighlighted()
            }
        }
    }

    inner class ElementViewHolder(context: Context) : RecyclerView.ViewHolder(ElementView(context))

    interface ElementListener {
        fun onElementSelected(element: Element, position: Int)
    }
}

