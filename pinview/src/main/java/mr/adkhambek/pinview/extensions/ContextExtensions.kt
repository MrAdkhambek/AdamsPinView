package mr.adkhambek.pinview.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

// Hello adam. Project AdamsPinView
// 23/06/2019 10:41

/**
 * Extension for get color by resource
 */
fun Context.getColor(@ColorRes id: Int): Int {
    return ContextCompat.getColor(this, id)
}

/**
 * Extension for get dimens to float by resource
 */

fun Context.getDimensionInPx(@DimenRes id: Int): Float {
    return resources.getDimension(id)
}

/**
 * Extension for get drawable by resource
 */

fun Context.getDrawable(@DrawableRes id: Int): Drawable? {
    return ContextCompat.getDrawable(this, id)
}

fun Context.toast(str: String) {
    Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
}