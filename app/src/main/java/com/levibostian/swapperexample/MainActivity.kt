package com.levibostian.swapperexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.levibostian.swapper.SwapperView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setupViews()
    }

    private fun setupViews() {
        swapper_view.swapTo(first_view)

        first_view_imageview.load("https://raw.githubusercontent.com/levibostian/Swapper-iOS/d494bc41894b5e5bc7eeacc162a96ddadca024cc/Example/Swapper/Images.xcassets/little_hill.imageset/little_hill.jpg")

        first_view_swap_button.setOnClickListener {
            swapper_view.swapTo(second_view) {
                second_view_imageview.load("https://raw.githubusercontent.com/levibostian/Swapper-iOS/d494bc41894b5e5bc7eeacc162a96ddadca024cc/Example/Swapper/Images.xcassets/mt_mckinley.imageset/mt_mckinley.jpg")
            }
        }

        second_view_swap_button.setOnClickListener {
            swapper_view.swapTo(first_view)
        }

        animation_duration_100.setOnClickListener { SwapperView.config.animationDuration = 100 }
        animation_duration_400.setOnClickListener { SwapperView.config.animationDuration = 400 }
        animation_duration_1s.setOnClickListener { SwapperView.config.animationDuration = 1 * 1000 }
        animation_duration_5s.setOnClickListener { SwapperView.config.animationDuration = 5 * 1000 }
    }
}
