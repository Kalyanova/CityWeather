package by.paranoidandroid.cityweather.view.transition

import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.ChangeTransform
import android.transition.TransitionSet

class ImageTransition: TransitionSet() {

    init {
        ordering = ORDERING_TOGETHER
        // ChangeBounds animates the bounds (location and size) of the view
        addTransition(ChangeBounds())
                // ChangeTransform animates the scale of the view, including the parent
                .addTransition(ChangeTransform())
                // ChangeImageTransform allows us to change the size (and/or scale type) of the image
                .addTransition(ChangeImageTransform())
    }
}