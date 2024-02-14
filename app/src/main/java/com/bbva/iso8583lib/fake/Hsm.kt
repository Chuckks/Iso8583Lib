package com.bbva.iso8583lib.fake

import com.bbva.devicelib.physical.IHsm
import javax.crypto.SecretKey

class Hsm: IHsm {

    enum class EStoreIndex{
        TMK,
        PIN,
        MAC,
        DATA,
        DUKPT_KEK,
        DUKPT_BDK,
        DUKPT_IPEK
    }

    override fun getKCV(storeIndex: Int) = byteArrayOf()

    override fun injectClearKey(type: IHsm.EKeyType, key: SecretKey, storeIndex: Int) = true

    override fun injectEncryptedKey( type: IHsm.EKeyType, encryptIndex: Int, key: SecretKey, storeIndex: Int) = true

    override fun encrypt(keyIndex: Int, mode: IHsm.EEncryptMode, data: ByteArray, iv: ByteArray): ByteArray {
        val length = if (data.size.rem(8) > 0)
            data.size + 8
        else
            data.size
        return ByteArray(length)
    }

    override fun decrypt(keyIndex: Int, mode: IHsm.EEncryptMode, data: ByteArray, iv: ByteArray): ByteArray {
        var length = data.size

        if (length.rem(8) > 0)
            length += 8

        return ByteArray(length)
    }

    override fun isAvailable(): Boolean = true
}