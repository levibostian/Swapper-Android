package com.levibostian.swapperexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.api.load
import com.levibostian.swapper.SwapperView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {

    enum class SwapperViews {
        FIRST_VIEW,
        SECOND_VIEW
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setupViews()
    }

    private fun setupViews() {
        swapper_view.apply {
            viewMap = mapOf(
                Pair(SwapperViews.FIRST_VIEW.name, first_view),
                Pair(SwapperViews.SECOND_VIEW.name, second_view)
            )
            swapTo(SwapperViews.FIRST_VIEW.name) {
                first_view_imageview.load("https://raw.githubusercontent.com/levibostian/Swapper-iOS/d494bc41894b5e5bc7eeacc162a96ddadca024cc/Example/Swapper/Images.xcassets/little_hill.imageset/little_hill.jpg")
            }
        }

        first_view_swap_button.setOnClickListener {
            swapper_view.swapTo(SwapperViews.SECOND_VIEW.name) {
                second_view_imageview.load("https://raw.githubusercontent.com/levibostian/Swapper-iOS/d494bc41894b5e5bc7eeacc162a96ddadca024cc/Example/Swapper/Images.xcassets/mt_mckinley.imageset/mt_mckinley.jpg")
            }
        }

        second_view_swap_button.setOnClickListener {
            swapper_view.swapTo(SwapperViews.FIRST_VIEW.name) {}
        }

        animation_duration_100.setOnClickListener { SwapperView.config.animationDuration = 100 }
        animation_duration_400.setOnClickListener { SwapperView.config.animationDuration = 400 }
        animation_duration_1s.setOnClickListener { SwapperView.config.animationDuration = 1 * 1000 }
        animation_duration_5s.setOnClickListener { SwapperView.config.animationDuration = 5 * 1000 }
    }

}
