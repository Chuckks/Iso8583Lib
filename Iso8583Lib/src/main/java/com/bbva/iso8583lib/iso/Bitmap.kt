package com.bbva.iso8583lib.iso

import com.bbva.iso8583lib.interfaces.IContenible
import kotlin.experimental.and

private const val BITS_PER_BYTE = 8

class Bitmap(size: Int): IContenible {

    constructor(value: ByteArray): this(BITS_PER_BYTE){
        this.value = value
    }

    var value: ByteArray = ByteArray(size)

    override fun clean() {
        value = ByteArray(value.size)
    }

    override fun isEmpty(): Boolean {
        for (index in 1..value.size) {
            if (isBitOn(index)) {
                return false
            }
        }
        return true
    }

    fun getSize(): Int = value.size

    fun isBitOn(nField: Int): Boolean {
        checkField(nField)
        var divBitMap: Int

          for (block in value.indices) {
              divBitMap = 0x80

              for (bit in 1..BITS_PER_BYTE) {
                  if ((block * BITS_PER_BYTE + bit) == nField) {
                      return (value[block] and divBitMap.toByte()) != 0.toByte()
                  }
                  divBitMap = (divBitMap shr 1)
              }
          }
          return false
    }

    fun setBitOn(nField: Int) {
        checkField(nField)
        for (block in value.indices) {
            var bmap = value[block].toByte()
            var divBitMap: Int = 0x80

            for (bit in 1..BITS_PER_BYTE) {
                if ((block * BITS_PER_BYTE + bit) == nField) {
                    bmap = (bmap + divBitMap).toByte()
                    value[block] = bmap
                    break
                }

                divBitMap = (divBitMap shr 1)
            }
        }
    }

    fun checkField(nField: Int){
        if(nField > BITS_PER_BYTE * value.size)
            throw IndexOutOfBoundsException ("nField [$nField] > MaxSIze [${BITS_PER_BYTE * value.size}]")

        if (nField < 1)
            throw IndexOutOfBoundsException("nField [$nField] < 1")
    }
}