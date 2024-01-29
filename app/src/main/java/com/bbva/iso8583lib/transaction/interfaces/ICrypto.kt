package com.bbva.iso8583lib.transaction.interfaces

import com.bbva.iso8583lib.transaction.enums.EReason

interface ICrypto {

    //Implement Crypto
    fun createCryptoRequest( ): EReason
    fun createCryptoResponse( ): EReason

    //Implement Encrypt Request
   fun encryptRequest(request: ByteArray ): ByteArray
   fun encryptRequest(request: String ): ByteArray

    //Implement Decrypt Request
    fun decryptResponse(response: ByteArray ): ByteArray
    fun decryptResponse(response: String ): ByteArray

}