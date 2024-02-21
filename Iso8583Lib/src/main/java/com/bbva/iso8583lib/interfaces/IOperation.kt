package com.bbva.iso8583lib.interfaces

import com.bbva.devicelib.emv.inputData.AmountData
import com.bbva.iso8583lib.module.EReason
import com.bbva.iso8583lib.utils.DateTime
import com.bbva.utilitieslib.interfaces.IEmpty

private const val DEFAULT_AMOUNT = 0L

private const val DEFAULT_VALUE = ""
private val DEFAULT_REASON = EReason.NONE

interface IOperation {

	data class InputData(var amountData: Long = DEFAULT_AMOUNT ): IEmpty {
		override fun isEmpty()= (amountData == DEFAULT_AMOUNT)
	}

	data class OutputData(
		var dateTime: String = DateTime.now().toString(),
		var codeError: String = DEFAULT_VALUE,
		var message: String = DEFAULT_VALUE,
		var reason: EReason = EReason.NONE,
		var result: Boolean = false,
		var operation: Any? = null):IEmpty {
		override fun isEmpty()=(message == DEFAULT_VALUE && reason == DEFAULT_REASON && operation == null)
	}

	//suspend fun execute(inputData: InputData): OutputData
	fun execute(inputData: InputData): OutputData
}