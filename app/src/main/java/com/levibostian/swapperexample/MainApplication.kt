package com.levibostian.swapperexample

import android.app.Application
import coil.Coil
import coil.ImageLoader

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Coil.setImageLoader {
            ImageLoader.Builder(this).apply {
                placeholder(R.drawable.ic_launcher_background)
                error(R.drawable.ic_launcher_foreground)
            }.build()
        }
    }
}
