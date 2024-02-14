package com.bbva.iso8583lib.extensions

fun Int.toHexByteArray(length: Int): ByteArray {
    val hexString = this.toString(16).padStart(length * 2, '0')

    return hexString.chunked(2) {
        Integer.parseInt(it.toString(), 16).toByte()
    }.toByteArray()
}