package by.paranoidandroid.cityweather.view.transition

import android.support.transition.*

class ImageTransition: TransitionSet() {

    init {
        val fadeIn = Fade()
        fadeIn.mode = Fade.MODE_OUT

        val fadeOut = Fade()
        fadeOut.mode = Fade.MODE_IN

        val changeBounds = ChangeBounds()

        val changeTransform = ChangeTransform()

        val changeClipBounds = ChangeClipBounds()

        val changeImageTransform = ChangeImageTransform()

        addTransition(fadeIn)
                setOrdering(ORDERING_TOGETHER)
                .addTransition(changeBounds)
                .addTransition(changeTransform)
                .addTransition(changeClipBounds)
                .addTransition(changeImageTransform)
                .addTransition(fadeOut)
    }
}