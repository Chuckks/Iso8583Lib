package com.bbva.iso8583lib.utils

class Convert {

    companion object {

        fun toHexaToAscii(source: ByteArray): ByteArray {
            val hexString = source.toString(Charsets.US_ASCII)
            val decimalValue = hexString.toLong(16)
            return decimalValue.toString().toByteArray(Charsets.US_ASCII)
        }
    }
}