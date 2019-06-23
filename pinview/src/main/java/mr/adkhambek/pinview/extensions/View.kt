package mr.adkhambek.pinview.extensions

import android.view.View
import androidx.annotation.DimenRes

// Hello adam. Project AdamsPinView
// 23/06/2019 11:19


/**
 * Extension for get dimens to float by resource
 */

fun View.getDimensionInPx(@DimenRes id: Int): Float {
    return resources.getDimension(id)
}