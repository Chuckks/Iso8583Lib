package com.bbva.iso8583lib.iso

class AbstractTransaction(private val packageIso: UnpackerIso) {

    protected val InputMsg =  InputMessage(packageIso)
    protected val OutputMsg =  OutputMessage(packageIso)
    protected var ResponseCode: Int = 0

}