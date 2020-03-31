package com.levibostian.swapper

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.annotation.TargetApi
import android.os.Build.VERSION_CODES
import android.view.View
import android.widget.FrameLayout

typealias SwapperViewId = String
typealias SwapperViewSwapAnimator = (oldView: View, newView: View, duration: Long, onComplete: () -> Unit) -> Animator

class SwapperView: FrameLayout {

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

    private var currentlyShownViewId: Pair<SwapperViewId, View>? = null

    private var _swapAnimatorAnimator: Animator? = null

    private val children: List<View>
    get() {
        val children: MutableList<View> = mutableListOf()

        for (index in 0 until childCount) {
            children.add(getChildAt(index))
        }

        return children
    }

    var viewMap: Map<SwapperViewId, View>? = null
        set(value) {
            field = value

            var currentlyShownViewFound = false
            value?.forEach { mapView ->
                val id = mapView.key
                val view = mapView.value

                children.firstOrNull { it == view } ?: throw IllegalArgumentException("Cannot add view, $mapView, as it's not a child.")
                if (id == currentlyShownViewId?.first) currentlyShownViewFound = true
            }

            if (!currentlyShownViewFound) hideAllChildren()
        }

    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        initialize(context, attrs, defStyleAttr)
    }
    @TargetApi(VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int): super(context, attrs, defStyleAttr, defStyleRes) {
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

    fun swapTo(id: SwapperViewId, onComplete: () -> Unit) {
        checkNotNull(viewMap) { "Can't swap to a view if you have not set viewMap" }
        if (currentlyShownViewId?.first == id) return

        val viewToSwapTo = viewMap!!.getValue(id)
        val currentlyShownView = currentlyShownViewId?.second

        fun doneWithAnimation() {
            viewToSwapTo.visibility = View.VISIBLE
            currentlyShownView?.visibility = View.GONE

            currentlyShownViewId = Pair(id, viewToSwapTo)

            onComplete()
        }

        if (currentlyShownView == null) {
            doneWithAnimation()
        } else {
            _swapAnimatorAnimator?.removeAllListeners()
            _swapAnimatorAnimator?.end()

            currentlyShownView.clearAnimation()
            viewToSwapTo.clearAnimation()

            _swapAnimatorAnimator = swapAnimator(currentlyShownView, viewToSwapTo, animationDuration) {
                doneWithAnimation()
            }
        }
    }

}