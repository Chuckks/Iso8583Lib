package com.bbva.iso8583lib.transaction.utils

private const val DEFAULT_HOUR = 0
private const val DEFAULT_MINUTE = 0
private const val DEFAULT_SECOND = 0

data class Time(
		val hour: Int = DEFAULT_HOUR,
		val minute: Int = DEFAULT_MINUTE,
		val second: Int = DEFAULT_SECOND,
		val nano: Int = 0 ) {

	override fun toString() = String.format("%02d:%02d:%02d", hour, minute, second)

	fun isEmpty() = (hour == DEFAULT_HOUR && minute == DEFAULT_MINUTE && second == DEFAULT_SECOND)

	companion object{
		val empty = Time()
	}
}