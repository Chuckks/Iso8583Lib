package com.bbva.iso8583lib.module

import com.bbva.iso8583lib.interfaces.IOperation
import com.bbva.iso8583lib.interfaces.ITransaction
import com.bbva.iso8583lib.iso.InputMessage
import com.bbva.iso8583lib.iso.OutputMessage
import com.bbva.iso8583lib.iso.UnpackerIso

abstract class AbstractTransaction(packageIso: UnpackerIso): ITransaction, IOperation {

    protected val inputMsg =  InputMessage(packageIso)
    protected val outputMsg =  OutputMessage(packageIso)

    protected fun packRequest( ) = outputMsg.pack()
    protected fun unpackResponse(request: ByteArray ) = inputMsg.unpack(request)
    override suspend fun execute(inputData: IOperation.InputData) = run( inputData )

    //Implements Package
    protected abstract fun packDefault( )
    protected abstract fun packField56()
    protected abstract fun packField63()
    protected abstract fun packSpecificFields(): EReason
    protected abstract fun unpackDefault()
    protected abstract fun unpackField63(): EReason
    protected abstract fun unpackSpecificFields(): EReason

    //Implement Crypto
    protected abstract fun createCryptoRequest(): EReason //TODO Inicializar Cifrado 3DES, DES...
    protected abstract fun createCryptoResponse(): EReason //TODO Inicializar Cifrado 3DES, DES...

    //Implement Encrypt Request
    protected abstract fun encryptRequest(request: ByteArray ): ByteArray
    protected abstract fun encryptRequest(request: String ): ByteArray

    //Implement Decrypt Request
    protected abstract fun decryptResponse(response: ByteArray ): ByteArray
    protected abstract fun decryptResponse(response: String ): ByteArray

}