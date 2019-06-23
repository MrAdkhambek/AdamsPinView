package mr.adkhambek.pinview.listeners

// Hello adam. Project anor-pos-back-office
// 15/06/2019 19:34

interface PinViewListener {

    fun onChange(subPin: String)

    fun onComplete(pin: String)

    fun onCancel()
}