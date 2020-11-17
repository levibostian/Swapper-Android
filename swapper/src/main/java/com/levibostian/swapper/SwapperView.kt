package com.levibostian.swapper

import android.annotation.TargetApi
import android.content.Context
import android.os.Build.VERSION_CODES
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

typealias SwapperViewSwapAnimator = (oldView: View, newView: View, duration: Long, onComplete: () -> Unit) -> Unit

class SwapperView : FrameLayout {

    companion object {
        val config: SwapperConfig = SwapperConfig
    }

    /**
     * Must use 2 variables to represent this so we load the default value at the time it's needed so you can change the default value anytime and it's used.
     */
    private var _animationDuration: Long? = null
    var animationDuration: Long
        set(value) {
            _animationDuration = value
        }
        get() {
            return _animationDuration ?: config.animationDuration
        }

    private var _swapAnimator: SwapperViewSwapAnimator? = null
    var swapAnimator: SwapperViewSwapAnimator
        set(value) {
            _swapAnimator = value
        }
        get() {
            return _swapAnimator ?: config.swapAnimator
        }

    private var currentlyShownViewId: Int? = null
    private val currentlyShownView: View?
        get() = children.filter { it.id == currentlyShownViewId }.firstOrNull()

    private val children: List<View>
        get() {
            val children: MutableList<View> = mutableListOf()

            for (index in 0 until childCount) {
                children.add(getChildAt(index))
            }

            return children
        }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(context, attrs, defStyleAttr)
    }
    @TargetApi(VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initialize(context, attrs, defStyleAttr)
    }

    private fun initialize(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        // Default behavior is to hide all views until you map views to an ID
        hideAllChildren()
    }

    private fun hideAllChildren() {
        children.forEach { childView ->
            childView.visibility = View.GONE
        }
    }

    @Synchronized fun swapTo(viewToSwapTo: View, onComplete: (() -> Unit)? = null) {
        if (!children.contains(viewToSwapTo)) throw IllegalArgumentException("View must be child to swap to it. Given: ${viewToSwapTo.id}, children: ${children.map { it.id }.joinToString(", ")}")
        if (currentlyShownViewId == id) return

        val isFirstView = currentlyShownViewId == null
        val oldView = currentlyShownView
        currentlyShownViewId = viewToSwapTo.id

        fun doneWithAnimation() {
            viewToSwapTo.visibility = View.VISIBLE
            oldView?.visibility = View.GONE

            onComplete?.invoke()
        }

        if (isFirstView) {
            hideAllChildren()
            doneWithAnimation()
        } else {
            swapAnimator(oldView!!, viewToSwapTo, animationDuration) {
                doneWithAnimation()
            }
        }
    }
}
