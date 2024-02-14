package com.bbva.iso8583lib.comms

interface ISocket {
    fun connect(): Boolean
    fun disconnect(): Boolean
    fun isOpened(): Boolean

    fun send(data: String): Boolean
    fun send(data: ByteArray): Boolean

    fun receive(): ByteArray
}
