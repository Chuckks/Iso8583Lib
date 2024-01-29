package com.bbva.iso8583lib.utils

import java.util.Calendar

private const val DEFAULT_MONTH = 1
private const val DATE_EMPTY = "0000-00-00T00:00:00.000Z"

data class DateTime(
	var date: Date = Date(),
	var time: Time = Time()
	) {
	fun isEmpty() = (date.isEmpty() && time.isEmpty())

	override fun toString(): String {
		return format(EFormat.DATE_TIME)
	}

	enum class EFormat(val value: String) {
		DATE_TIME("dd/MM/yyyy HH:mm:ss"),
		ISO8601("yyyy-MM-dd'T'HH:mm:ss.sss'Z'")
	}

	private fun setDefaultDateTime(){
		val dateTime = now()
		date =dateTime.date
		time = dateTime.time
	}

	fun format(format:EFormat):String{
		if( isEmpty() )
			setDefaultDateTime()

		return when(format){
			EFormat.DATE_TIME->String.format("%02d/%02d/%04d %02d:%02d:%02d",date.day,date.month,date.year, time.hour,time.minute,time.second)
			EFormat.ISO8601->String.format("%04d-%02d-%02dT%02d:%02d:%02d.%03dZ",date.year,date.month,date.day, time.hour,time.minute,time.second,time.nano)
		}
	}

	companion object {

		const val DEFAULT_CONST_DATE = "1990-01-01T00:00:00.000Z"

		fun now():DateTime{
			val dateNow = Calendar.getInstance( )

			return	DateTime(Date(dateNow.get(Calendar.YEAR),dateNow.get(Calendar.MONTH) + DEFAULT_MONTH,
					dateNow.get(Calendar.DAY_OF_MONTH)),Time(dateNow.get(Calendar.HOUR_OF_DAY),dateNow.get(Calendar.MINUTE),
					dateNow.get(Calendar.SECOND),dateNow.get(Calendar.MILLISECOND) ))
		}

		fun checkDateStr( date: String ) = ( date == DATE_EMPTY )
	}
}
