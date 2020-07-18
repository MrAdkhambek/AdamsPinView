package mr.adkhambek.pinview.view

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.view.setMargins
import mr.adkhambek.pinview.R
import mr.adkhambek.pinview.extensions.getDimensionInPx
import mr.adkhambek.pinview.listeners.PinViewListener


/**
 * PinView
 */
class PinView(context: Context, attrs: AttributeSet? = null) : GridLayout(context, attrs) {

    private var pin = ""
    private var dots: PinViewDots? = null
    private var listener: PinViewListener? = null

    private var lp: LinearLayout.LayoutParams

    private var mPinLength: Int = 0

    private var itemSize = 0
    private var itemMargin = 0
    private var itemTextColor = 0
    private var itemBackground: Drawable? = null

    private var cancelButtonColor = 0
    private var cancelButtonTextSize = 0
    private var cancelButtonVisible = true
    private var cancelButtonText: String? = null
    private var cancelButtonBackground: Drawable? = null

    private var removeButtonPadding = 0
    private var removeButtonDrawable = 0
    private var removeButtonBackground: Drawable? = null

    /**
     *  PinView Listener
     */
    fun setPinListener(l: PinViewListener) {
        this.listener = l
    }

    init {

        pin = ""
        columnCount = COLUMN_COUNT

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.PinView, 0, 0)

        mPinLength =
            typedArray.getInt(R.styleable.PinView_mPinLength, PinViewDots.DEFAULT_PIN_LENGTH)

        /**
         *  Numbers parameters
         */

        itemBackground = typedArray.getDrawable(R.styleable.PinView_android_itemBackground)
            ?: context.getDrawable(R.drawable.item_back)

        itemMargin = typedArray.getDimension(
            R.styleable.PinView_itemMargin,
            context.getDimensionInPx(R.dimen.item_margin)
        ).toInt()

        itemSize = typedArray.getDimension(
            R.styleable.PinView_itemSize,
            context.getDimensionInPx(R.dimen.item_size)
        ).toInt()

        itemTextColor = typedArray.getColor(
            R.styleable.PinView_itemTextColor,
            Color.BLACK
        )


        /**
         *  Cancel button parameters
         */

        cancelButtonColor = typedArray.getColor(
            R.styleable.PinView_cancelButtonColor,
            Color.BLACK
        )

        cancelButtonTextSize = typedArray.getDimension(
            R.styleable.PinView_cancelButtonTextSize,
            context.getDimensionInPx(R.dimen.cancel_button_text_size_size)
        ).toInt()

        cancelButtonVisible = typedArray.getBoolean(R.styleable.PinView_cancelButtonVisible, true)

        cancelButtonText = typedArray.getString(R.styleable.PinView_cancelButtonText)
            ?: context.getString(R.string.cancel)

        cancelButtonBackground = typedArray.getDrawable(R.styleable.PinView_cancelButtonBackground)


        /**
         *  Remove button parameters
         */

        removeButtonDrawable =
            typedArray.getResourceId(R.styleable.PinView_removeButtonDrawable, R.drawable.ic_delete)

        removeButtonPadding = typedArray.getDimension(
            R.styleable.PinView_removeButtonPadding,
            context.getDimensionInPx(R.dimen.cancel_button_text_size_size)
        ).toInt()

        removeButtonBackground = typedArray.getDrawable(R.styleable.PinView_removeButtonBackground)

        lp = LinearLayout.LayoutParams(itemSize, itemSize).apply {
            setMargins(itemMargin.px)
        }

        initialNumbers()
        initialButtons()

        typedArray.recycle()
    }

    private fun initialNumbers() {
        for (i: Int in 1..9) {
            addView(TextView(context, null, 0, R.style.btn_pin_style).apply {
                text = i.toString()
                layoutParams = lp
                setTextColor(itemTextColor)
                background = itemBackground

                setOnClickListener { onClick(i) }
            })
        }
    }

    private fun initialButtons() {

        val removeBtn = ImageButton(context).apply {
            layoutParams = lp
//            setMargins(removeButtonPadding.px)
            background = removeButtonBackground
            setImageResource(removeButtonDrawable)

            setOnClickListener { onClick(12) }
        }

        val cancelBtn = TextView(context, null, 0, R.style.btn_pin_style).apply {
            textSize = cancelButtonTextSize.toFloat()
            layoutParams = lp
            text = cancelButtonText
            setTextColor(cancelButtonColor)
            isVisible = cancelButtonVisible
            background = cancelButtonBackground

            setOnClickListener { onClick(10) }
        }

        val nolBtn = TextView(context, null, 0, R.style.btn_pin_style).apply {
            text = "0"
            layoutParams = lp
            setTextColor(itemTextColor)
            background = itemBackground

            setOnClickListener { onClick(11) }
        }

        addView(cancelBtn)
        addView(nolBtn)
        addView(removeBtn)
    }

    private val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    private fun onClick(position: Int) {
        when (position) {
            10 -> listener?.onCancel()

            11 -> {
                if (pin.length < PinViewDots.DEFAULT_PIN_LENGTH) pin += 0
            }

            12 -> pin = pin.dropLast(1)

            else -> {
                if (pin.length < mPinLength) pin += position
            }
        }

        if (pin.length == mPinLength) {
            listener?.onComplete(pin)
            dots?.updateDot(pin.length)
        } else {
            listener?.onChange(pin)
            dots?.updateDot(pin.length)
        }
    }

    /**
     * if pin error call
     */
    fun error() {
        pin = ""
        listener?.onChange(pin)

        val shake: Animation = AnimationUtils.loadAnimation(context, R.anim.shake)
        startAnimation(shake)
        dots?.startAnimation(shake)

        shakeItBaby()
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
    }

    private fun shakeItBaby() {
        if (Build.VERSION.SDK_INT >= 26) {
            (context?.getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(
                VibrationEffect.createOneShot(
                    150,
                    10
                )
            )
        } else {
            (context?.getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(150)
        }
    }

    /**
     * Attach PinViewDots
     */
    fun attachDots(dots: PinViewDots) {
        this.dots = dots
        setPinLength(dots.getPinLength())
    }

    companion object {
        /**
         * Default Column count
         */
        const val COLUMN_COUNT: Int = 3
    }
}