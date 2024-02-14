package com.bbva.iso8583lib.transaction.operations

import android.util.Log
import com.bbva.devicelib.emv.inputData.AmountData
import com.bbva.devicelib.emv.outputData.PemData
import com.bbva.iso8583lib.module.EReason
import com.bbva.iso8583lib.transaction.Iso
import com.bbva.iso8583lib.transaction.enums.EOperation
import com.bbva.iso8583lib.utils.Constant
import com.bbva.utilitieslib.extensions.toHexaString

private var TAG = Constant.ISO_PRFIX + Operations::class.java.simpleName

private val DEFAULT_FIELD = ByteArray(0)

class Operations: Iso {
    constructor(operData: EOperation) : super(operData)

    protected var field39: Int = 0
    protected val amounts = AmountData()

    protected val pan = ByteArray(0)
    protected val pem = PemData()

    protected val trackI = ByteArray(0)
    protected val trackII = ByteArray(0)
    protected val pinBlock = ByteArray(0)

    protected var field55 = DEFAULT_FIELD
    protected var field56 = DEFAULT_FIELD
    protected val field58 = DEFAULT_FIELD

    protected val field60 = DEFAULT_FIELD
    protected var field63 = DEFAULT_FIELD

    override fun packSpecificFields(): EReason {
        pan.let { outputMsg.setField(2, encryptRequest(it)) }
        amounts.amount.let { outputMsg.setField(4, it) }

        pem.let {/*utputMsg.setField(22, pem.toAsciiToBcd()) */}
        trackII.let { outputMsg.setField(35, encryptRequest(it)) }

        trackI.let { outputMsg.setField(45, encryptRequest(it)) }
        outputMsg.setField(39, field39)

        pinBlock.let { outputMsg.setField(52, it) }
        amounts.otherAmount.let { outputMsg.setField(54, it) }

        field55.let { outputMsg.setField(55, it) }
        field56.let { outputMsg.setField(56, it) }
        field58.let { outputMsg.setField(58, it) }

        field60.let { outputMsg.setField(60, it) }
        field63.let { outputMsg.setField(63, it) }

        return EReason.PACK_SPECIFIC_FIELD_SUCCESS
    }

    override fun unpackSpecificFields(): EReason {

        if( !inputMsg.isEmpty( 55 ) ) {
            field55 = inputMsg.getField(55)
            Log.i(TAG, "Field 55 [${field55.toHexaString()}] -> Size [${field55.size}]")
        } else field55 = DEFAULT_FIELD

        if (!inputMsg.isEmpty(56)){
            field56 = inputMsg.getField(56)
            Log.i(TAG, "Field 56 [${field56.toHexaString()}] -> Size [${field56.size}]")
        } else field56 = DEFAULT_FIELD

        if (!inputMsg.isEmpty(58)){
            field56 = inputMsg.getField(58)
            Log.i(TAG, "Field 58 [${field58.toHexaString()}] -> Size [${field58.size}]")
        } else field56 = DEFAULT_FIELD

        if (!inputMsg.isEmpty(63)){
            field56 = inputMsg.getField(63)
            Log.i(TAG, "Field 63 [${field63.toHexaString()}] -> Size [${field63.size}]")
        } else field63 = DEFAULT_FIELD

        return EReason.UNPACK_SPECIFIC_FIELD_SUCCESS
    }
}