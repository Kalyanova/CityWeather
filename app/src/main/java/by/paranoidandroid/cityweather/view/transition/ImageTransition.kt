package by.paranoidandroid.cityweather.view.transition

/*import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.ChangeTransform
import android.transition.TransitionSet*/

import android.support.transition.*


class ImageTransition: TransitionSet() {

    init {
        /*val fadeIn = Fade()
        fadeIn.duration = 2000
        fadeIn.mode = Fade.MODE_OUT

        val fadeOut = Fade()
        fadeOut.duration = 2000
        fadeOut.mode = Fade.MODE_IN*/

        val changeBounds = ChangeBounds()
        changeBounds.duration = 2000

        val changeTransform = ChangeTransform()
        changeTransform.duration = 2000

        val changeClipBounds = ChangeClipBounds()
        changeClipBounds.duration = 2000

        val changeImageTransform = ChangeImageTransform()
        changeImageTransform.duration = 2000


//        ordering = ORDERING_TOGETHER
        //addTransition(fadeIn)
                setOrdering(ORDERING_TOGETHER)
                .addTransition(changeBounds)
                .addTransition(changeTransform)
                .addTransition(changeClipBounds)
                .addTransition(changeImageTransform)
                //.addTransition(fadeOut)



        // ChangeBounds animates the bounds (location and size) of the view
        /*
        ordering = ORDERING_TOGETHER
        addTransition(ChangeBounds())
                // ChangeTransform animates the scale of the view, including the parent
                .addTransition(ChangeTransform())
                // ChangeImageTransform allows us to change the size (and/or scale type) of the image
                .addTransition(ChangeImageTransform())*/
    }
}