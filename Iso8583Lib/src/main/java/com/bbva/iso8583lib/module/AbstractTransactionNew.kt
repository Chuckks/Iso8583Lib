package com.bbva.iso8583lib.module

import com.bbva.iso8583lib.comms.ServerData
import com.bbva.iso8583lib.comms.SocketServer
import com.bbva.iso8583lib.iso.InputMessage
import com.bbva.iso8583lib.iso.OutputMessage
import com.bbva.iso8583lib.iso.UnpackerIso

private val DEFAULt_RESPONSE_CODE = ByteArray(0)

open class AbstractTransactionNew {

    constructor(packageIso: UnpackerIso) : super(){
        socket = SocketServer(ServerData())
        responseCode = DEFAULt_RESPONSE_CODE
        inputMsg = InputMessage(packageIso)
        outputMsg = OutputMessage(packageIso)
    }

    constructor(packageIso: UnpackerIso, server: ServerData) : super(){
        socket = SocketServer(server)
        responseCode = DEFAULt_RESPONSE_CODE
        inputMsg = InputMessage(packageIso)
        outputMsg = OutputMessage(packageIso)
    }

    protected var inputMsg:  InputMessage
    protected var outputMsg: OutputMessage

    protected var responseCode: ByteArray
    protected var socket: SocketServer
}