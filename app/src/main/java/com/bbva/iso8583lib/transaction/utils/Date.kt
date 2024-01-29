package com.pagatodoholdings.sondalib.network.data.sonda.response

private const val DEFAULT_YEAR = 0
private const val DEFAULT_MONTH = 0
private const val DEFAULT_DAY = 0

data class Date(
		val year: Int = DEFAULT_YEAR,
		val month: Int = DEFAULT_MONTH,
		val day: Int = DEFAULT_DAY ) {

	override fun toString() = String.format("%02d/%02d/%04d", day, month, year)

	fun isEmpty() =(year == DEFAULT_YEAR && month == DEFAULT_MONTH && day == DEFAULT_DAY)
}
