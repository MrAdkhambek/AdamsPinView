package mr.adkhambek.adamspinview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import mr.adkhambek.pinview.extensions.toast
import mr.adkhambek.pinview.listeners.PinViewListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pinView.attachDots(dots)

        pinView.setPinListener(object : PinViewListener {

            override fun onChange(subPin: String) {
                Log.d("TTT", subPin)
            }

            override fun onComplete(pin: String) {
                toast(pin)
            }

            override fun onCancel() {
                toast("Cancel")
            }
        })
    }
}
