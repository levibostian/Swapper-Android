package com.levibostian.swapper

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import android.animation.AnimatorSet

object SwapperConfig {

    var animationDuration: Long = 200

    var swapAnimator: SwapperViewSwapAnimator = { oldView, newView, duration, onComplete ->
        val fadeOut = ObjectAnimator.ofFloat(oldView, "alpha", 1f, 0f).setDuration(duration).apply {
            addListener(object: Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    newView.visibility = View.GONE
                }
                override fun onAnimationEnd(animation: Animator) {
                    oldView.visibility = View.GONE
                }
                override fun onAnimationCancel(animation: Animator) {
                }
                override fun onAnimationRepeat(animation: Animator) {
                }
            })
        }

        val fadeIn = ObjectAnimator.ofFloat(newView, "alpha", 0f, 1f).setDuration(duration).apply {
            addListener(object: Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    newView.visibility = View.VISIBLE
                }
                override fun onAnimationEnd(animation: Animator) {
                    onComplete()
                }
                override fun onAnimationCancel(animation: Animator) {
                }
                override fun onAnimationRepeat(animation: Animator) {
                }
            })

            startDelay = duration
        }

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(fadeOut, fadeIn)

        animatorSet.start()
    }

}