package mr.adkhambek.pinview.extensions

import android.view.View
import androidx.annotation.DimenRes


/**
 * Extension for get dimens to float by resource
 */

fun View.getDimensionInPx(@DimenRes id: Int): Float {
    return resources.getDimension(id)
}