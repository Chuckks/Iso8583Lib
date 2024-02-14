package com.bbva.iso8583lib.transaction

import android.util.Log
import com.bbva.iso8583lib.interfaces.IOperation
import com.bbva.iso8583lib.module.EReason
import com.bbva.iso8583lib.module.Iso8583
import com.bbva.iso8583lib.transaction.enums.EOperation
import com.bbva.iso8583lib.utils.Constant

private val TAG = Constant.BBVA_PREFIX + IsoOperation::class.java.simpleName

//class IsoOperation(operData: EOperation, private val success: EReason, private val fail: EReason):
abstract class IsoOperation(operData: EOperation): BaseIsoOperation(Iso8583.IsoPackage, operData) {

    override suspend fun buildOutputError(reason: EReason) =
        IOperation.OutputData(reason = reason).apply {
            message = ""
            operation = operData.name
            result = false
            Log.i(TAG, "BuildOutputError -> [$this]")
        }

    override suspend fun buildOutput(reason: EReason) =
        IOperation.OutputData(reason = reason).apply {
            message = ""
            operation = operData.name
            result = true
            Log.i(TAG, "BuildOutput -> [$this]")
        }


    override fun printTransaction() { }
    override fun printTransactionError() { }

    override suspend fun saveTransaction() { }
    override suspend fun saveTransactionError() = saveTransaction()
}