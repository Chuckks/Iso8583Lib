package com.bbva.iso8583lib.transaction

import android.util.Log
import com.bbva.iso8583lib.App
import com.bbva.iso8583lib.module.Iso8583
import com.bbva.iso8583lib.transaction.enums.EOperation
import com.bbva.iso8583lib.transaction.enums.EReason
import com.bbva.iso8583lib.transaction.interfaces.IOperation

class BbvaTransDemo(private val operation: EOperation) {
    private lateinit var txOperation: IOperation

    suspend fun run(inputData: IOperation.InputData = IOperation.InputData()): IOperation.OutputData {


        if (!Iso8583.init)
            return IOperation.OutputData(reason = EReason.NOT_INIT_MODULE)

        txOperation = App.createOperation(operation)
        return innerRun(inputData)
    }

    private suspend  fun innerRun(inputData: IOperation.InputData): IOperation.OutputData {
        var outputData = txOperation.execute(inputData)

        Log.i("BbvaTransaction", "result -> $outputData")
        return outputData
    }
}