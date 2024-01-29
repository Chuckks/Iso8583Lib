package com.bbva.iso8583lib.transaction.interfaces

import com.bbva.iso8583lib.interfaces.IEmpty
import com.bbva.iso8583lib.transaction.enums.EOperation
import com.bbva.iso8583lib.transaction.enums.EReason
import com.pagatodoholdings.sondalib.network.data.sonda.response.DateTime

private const val DEFAULT_AMOUNT = 0L

private const val DEFAULT_MESSAGE = ""
private val DEFAULT_REASON = EReason.NONE

interface IOperation {

	data class InputData(var amount: Long = DEFAULT_AMOUNT ): IEmpty {
		override fun isEmpty()= (amount == DEFAULT_AMOUNT)
	}

	data class OutputData(
		var dateTime: String = DateTime.now().toString(),
		var message: String = DEFAULT_MESSAGE,
		var reason: EReason = EReason.NONE,
		var operation: EOperation = EOperation.NONE):IEmpty {
		override fun isEmpty()=(message == DEFAULT_MESSAGE && reason == DEFAULT_REASON && operation == EOperation.NONE)
	}

	var operation: EOperation

	suspend fun execute(inputData: InputData): OutputData
}