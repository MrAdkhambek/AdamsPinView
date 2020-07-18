package mr.adkhambek.pinview.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import mr.adkhambek.pinview.R
import mr.adkhambek.pinview.extensions.getDimensionInPx


/**
 * Dots for PinView
 */
class PinViewDots(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

    private var mPinLength: Int = 0
    private var mDotSpacing: Int = 0
    private var mDotDiameter: Int = 0
    private var mPreviousLength: Int = 0

    private var mFillDrawable: Drawable? = null
    private var mEmptyDrawable: Drawable? = null

    init {

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.PinViewDots, 0, 0)

        mDotSpacing = typedArray.getDimension(
            R.styleable.PinViewDots_dotSpacing,
            context.getDimensionInPx(R.dimen.dot_spacing)
        ).toInt()

        mDotDiameter = typedArray.getDimension(
            R.styleable.PinViewDots_dotDiameter,
            context.getDimensionInPx(R.dimen.dot_diameter)
        ).toInt()


        mFillDrawable = typedArray.getDrawable(R.styleable.PinViewDots_fillDotDrawable)
            ?: context.getDrawable(R.drawable.filled)

        mEmptyDrawable = typedArray.getDrawable(R.styleable.PinViewDots_fillDotDrawable)
            ?: context.getDrawable(R.drawable.empty)

        mPinLength = typedArray.getInt(R.styleable.PinViewDots_pinLength, DEFAULT_PIN_LENGTH)

        typedArray.recycle()
        initView(context)
    }

    private fun initView(context: Context) {
        ViewCompat.setLayoutDirection(this, ViewCompat.LAYOUT_DIRECTION_LTR)

        val params = LayoutParams(mDotDiameter, mDotDiameter).apply {
            setMargins(mDotSpacing, 0, mDotSpacing, 0)
        }

        for (i in 0 until mPinLength) {
            addView(View(context).apply {
                emptyDot()
                layoutParams = params
            })
        }
    }

    /**
     * Update dot
     */
    fun updateDot(length: Int) {
        mPreviousLength = if (length > 0) {
            if (length > mPreviousLength) {
                getChildAt(length - 1).fillDot()
                length - 1
            } else {
                getChildAt(length).emptyDot()
                length
            }
        } else {
            for (i in 0 until childCount) {
                getChildAt(i).emptyDot()
            }
            0
        }
    }

    private fun View.emptyDot() {
        background = mEmptyDrawable
    }

    private fun View.fillDot() {
        background = mFillDrawable
    }

    /**
     * Get length pin dots
     */
    fun getPinLength(): Int = mPinLength

    /**
     * Set length pin dots
     */
    fun setPinLength(pinLength: Int) {
        this.mPinLength = pinLength
        removeAllViews()
        initView(context)
    }

    companion object {
        /**
         * Default length for pinDots
         */
        const val DEFAULT_PIN_LENGTH: Int = 6
    }
}