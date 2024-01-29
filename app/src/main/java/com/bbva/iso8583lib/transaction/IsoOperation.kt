package com.bbva.iso8583lib.transaction


import com.bbva.iso8583lib.module.Iso8583
import com.bbva.iso8583lib.transaction.enums.EOperation
import com.bbva.iso8583lib.transaction.interfaces.IOperation
import com.bbva.iso8583lib.transaction.interfaces.ITransaction

class IsoOperation(var operation: EOperation ){

    var responseCode: Int = -1
    val server: ServerData = ServerData()

}