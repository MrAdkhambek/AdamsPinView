package mr.adkhambek.pinview.listeners


interface PinViewListener {

    fun onChange(subPin: String)

    fun onComplete(pin: String)

    fun onCancel()
}