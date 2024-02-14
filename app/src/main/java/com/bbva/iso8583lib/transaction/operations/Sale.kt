package com.bbva.iso8583lib.transaction.operations

import com.bbva.iso8583lib.interfaces.IOperation
import com.bbva.iso8583lib.module.EReason
import com.bbva.iso8583lib.transaction.Iso
import com.bbva.iso8583lib.transaction.enums.EOperation
import com.bbva.utilitieslib.extensions.toHexaBytes

class Sale: Iso(EOperation.SALE) {

    override suspend fun initialize(inputData: IOperation.InputData): EReason {
        outputMsg.setField(4, inputData.amountData.toInt())
        return super.initialize(inputData)
    }

    override fun packField60(){
        outputMsg.setField(60, "03")
    }

    override fun packSpecificFields(): EReason {
        outputMsg.setField(37, "HcEUm3qGmZog".toByteArray())
        return EReason.PACK_SPECIFIC_FIELD_SUCCESS
    }

}