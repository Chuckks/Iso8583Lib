package com.bbva.iso8583lib.module

import com.bbva.iso8583lib.interfaces.IIsoTransaction
import com.bbva.iso8583lib.interfaces.IOperation
import com.bbva.iso8583lib.iso.InputMessage
import com.bbva.iso8583lib.iso.OutputMessage
import com.bbva.iso8583lib.iso.UnpackerIso

abstract class AbstractTransaction: IIsoTransaction, IOperation {

    constructor(packageIso: UnpackerIso) : super(){
        this.packageIso = packageIso
    }

    private var packageIso: UnpackerIso = UnpackerIso()
    protected val inputMsg =  InputMessage(packageIso)
    protected val outputMsg =  OutputMessage(packageIso)

    override suspend fun execute(inputData: IOperation.InputData) = run( inputData )

    //Implements Package

    protected abstract fun packSpecificFields(): EReason
    protected abstract fun unpackSpecificFields(): EReason

    protected abstract fun packDefault( )
    protected abstract fun packField55()
    protected abstract fun packField56()
    protected abstract fun packField60()
    protected abstract fun packField63()

    protected abstract fun unpackDefault()
    protected abstract fun unpackField63()

    //Implement Encrypt Request
    protected abstract fun encryptRequest(request: ByteArray ): ByteArray
    protected abstract fun encryptRequest(request: String ): ByteArray

    //Implement Decrypt Request
    protected abstract fun decryptResponse(response: ByteArray ): ByteArray
    protected abstract fun decryptResponse(response: String ): ByteArray

}