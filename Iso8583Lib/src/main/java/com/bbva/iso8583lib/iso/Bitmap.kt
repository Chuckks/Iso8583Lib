package com.bbva.iso8583lib.iso

import com.bbva.iso8583lib.interfaces.IContenible
import kotlin.experimental.and

private const val BITS_PER_BYTE = 8

class Bitmap(size: Byte): IContenible {

    constructor(value: ByteArray): this(BITS_PER_BYTE.toByte()){
        this.value = value
    }

    var value: ByteArray = byteArrayOf(size)

    override fun clean() {
        value = ByteArray(value.size)
    }

    override fun isEmpty(): Boolean {
        for (index in 1..value.size) {
            if (isBitOn(index.toByte())) {
                return false
            }
        }
        return true
    }

    fun getSize(): Int = value.size

    fun isBitOn(nField: Byte): Boolean {
        checkField(nField)
        var divBitMap: Byte

        for (block in 0 until value.size) {
            divBitMap = 0x80.toByte()

            for (bit in 1..BITS_PER_BYTE) {
                if ((block * BITS_PER_BYTE + bit).toByte() == nField) {
                    return (value[block].toByte() and divBitMap) != 0.toByte()
                }
                divBitMap = (divBitMap.toInt() shr 1).toByte()
            }
        }
        return false
    }

    fun setBitOn(nField: Byte) {
        checkField(nField)
        for (block in 0 until value.size) {
            var bmap = value[block].toByte()
            var divBitMap: Byte = 0x80.toByte()

            for (bit in 1..BITS_PER_BYTE) {
                if ((block * BITS_PER_BYTE + bit).toByte() == nField) {
                    bmap = (bmap + divBitMap).toByte()
                    value[block] = bmap.toByte()
                    break
                }

                divBitMap = (divBitMap.toInt() shr 1).toByte()
            }
        }
    }

    fun checkField(nField: Byte){
        if(nField > BITS_PER_BYTE * value.size)
            throw IndexOutOfBoundsException ("nField [$nField] > MaxSIze [${BITS_PER_BYTE * value.size}]")

        if (nField < 1)
            throw IndexOutOfBoundsException("nField [$nField] < 1")
    }
}