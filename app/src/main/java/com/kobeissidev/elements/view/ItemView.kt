package com.kobeissidev.elements.view

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import android.widget.ProgressBar
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
    private var progressBar: ProgressBar? = null
    private var progressDuration = 0L

    private val progressAnimation by lazy {
        ObjectAnimator.ofInt(progressBar, "progress", 0, 100).apply {
            duration = progressDuration * MILLISECOND
            interpolator = DecelerateInterpolator()
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {
                    progressBar?.visibility = View.VISIBLE
                }

                override fun onAnimationEnd(animator: Animator) {
                    progressBar?.visibility = View.GONE
                }

                override fun onAnimationCancel(animator: Animator) = Unit
                override fun onAnimationRepeat(animator: Animator) = Unit
            })
        }
    }

    private companion object {
        const val MILLISECOND = 1000L
    }

    init {
        // Inflate the layout.
        View.inflate(context, R.layout.view_item, this)
    }

    /**
     * Bind the element to the button.
     */
    fun bind(item: Item) = with(item) {
        // Initialize the item button and set the text to the name of the item.
        itemButton = findViewById<MaterialButton>(R.id.item_button).also { it.text = name }
        progressBar = findViewById<ProgressBar>(R.id.item_progress).also {
            it.max = duration.toInt()
        }
        progressDuration = duration
    }

    /**
     * Highlight the element button and "Play".
     */
    fun onClicked() = itemButton?.let {
        it.highlight()
        progressAnimation.start()
    }

    /**
     * Remove the highlight on the item button and "Unplay".
     */
    fun onUnClicked() = itemButton?.let {
        val colorUnHighlight = ContextCompat.getColor(context, R.color.colorUnHighlight)
        it.backgroundTintList = ColorStateList.valueOf(colorUnHighlight)
        progressAnimation.cancel()
    }

    /**
     * Interface to inform the listeners of changes.
     */
    interface ItemListener {
        fun onItemSelected(position: Int)
    }
}