[![Download](https://api.bintray.com/packages/levibostian/Swapper/com.levibostian.swapper/images/download.svg)](https://bintray.com/levibostian/Swapper/com.levibostian.swapper/_latestVersion)
[![GitHub license](https://img.shields.io/github/license/levibostian/Swapper-Android.svg)](https://github.com/levibostian/Swapper-Android/blob/master/LICENSE)

# Swapper

Swap between many different Android Views within your app quick and easy.

![project logo](misc/header.jpg)

*iOS developer? Check out [the iOS version of Swapper!](https://github.com/levibostian/swapper-ios)*

## What is Swapper?

You know those moments in your app when you have a `RecyclerView` that has no rows to show? You know those moments when you perform a HTTP network request and you want to show a non-blocking loading view to the user? These are very common scenarios for mobile apps. Swapper is an Android `View` that allows you to swap between a set of other `Views`s with just 1 line of code.

![demo gif](misc/demo.gif)

## Why use Swapper?

* Kotlin API
* Lightweight. Zero dependencies.
* UI testing friendly.
* Setup with default values that should work for 95% of your use cases. Fully customizable for those other cases.
* Strict semantic versioning for backwards compatibility. 
* Example app in this project to learn from. 

I recommend you check out 2 other libraries that work nicely with Swapper: [Empty](https://github.com/levibostian/Empty-Android) and [PleaseHold](https://github.com/levibostian/PleaseHold-Android).

## Installation

To install Swapper, simply add the following line to your `build.gradle` file:

```
implementation 'com.levibostian.swapper:swapper:version-goes-here'
```

Replace `version-here` with: [![Download](https://api.bintray.com/packages/levibostian/Swapper/com.levibostian.swapper/images/download.svg)](https://bintray.com/levibostian/Swapper/com.levibostian.swapper/_latestVersion) which is the latest version at this time.

## Getting started

* Add an instance of `SwapperView` to your layout. Add all of the views that you want to swap between as children of `SwapperView`.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.levibostian.swapper.SwapperView
        android:id="@+id/swapper_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <ImageView
            android:id="@+id/first_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>

        <ImageView
            android:id="@+id/second_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>
    </com.levibostian.swapper.SwapperView>

</LinearLayout>
```

> Note: The views you want to swap between **must be children** of `SwapperView`.
> Note: You can programmatically add more children into `SwapperView`. It's just a `ViewGroup`. 

* Swap to views:

```kotlin
class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setupViews()
    }

    private fun setupViews() {
        // Make sure to set the first swapping view:
        swapper_view.swapTo(first_view)
        // Note: the first time you call swapTo() there will *not* be an animation. 

        // ... do stuff ....
        // Anytime you want to swap to another View:
        swapper_view.swapTo(second_view) {
            // optional callback when animation is done. 
        }
    }

}
```

## Customize Swapper

#### Set animation duration

* Set the default animation duration used for all instances of `SwapperView`:

```kotlin
// Value is in milliseconds
SwapperView.config.animationDuration = 100
```

* Or, you can override the default for each instance of `SwapperView`:

```kotlin
swapper_view_instance.animationDuration = 500
```

#### Set the animation between the old and new view

* Set the default animation lambda used for all instances of `SwapperView`:

```kotlin
SwapperView.config.swapAnimator = { oldView, newView, duration, onComplete ->
    // Start animations here. When the animations are done running, call `onComplete()`
}
```

* Or, you can override the default for each instance of `SwapperView`:

```kotlin
swapper_view_instance.swapAnimator = { oldView, newView, duration, onComplete ->
   // Start animations here. When the animations are done running, call `onComplete()`
}
```

## Example

Swapper comes with an example app you can use to play with the library. To run the example project, clone the repo and then open it up in Android Studio.

## Development

* Install git hooks: `./hooks/autohook.sh install`
* Open up Swapper project in Android Studio. 
* Get to writing code!

## Contributors 

Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key))

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tr>
    <td align="center"><a href="https://github.com/levibostian"><img src="https://avatars1.githubusercontent.com/u/2041082?v=4" width="100px;" alt=""/><br /><sub><b>Levi Bostian</b></sub></a><br /><a href="https://github.com/levibostian/Purslane/commits?author=levibostian" title="Code">💻</a> <a href="https://github.com/levibostian/Purslane/commits?author=levibostian" title="Documentation">📖</a> <a href="#maintenance-levibostian" title="Maintenance">🚧</a></td>
  </tr>
</table>

<!-- markdownlint-enable -->
<!-- prettier-ignore-end -->
<!-- ALL-CONTRIBUTORS-LIST:END -->

## Contribute

**Want to add features to Swapper?** Before you decide to take a bunch of time and add functionality to the library, please, [create an issue](https://github.com/levibostian/Swapper-android/issues/new) stating what you wish to add. This might save you some time in case your purpose does not fit well in the use cases of Swapper.

## License

Swapper is available under the MIT license. See the LICENSE file for more info.
