package com.bbva.iso8583lib.iso.data

import com.bbva.utilitieslib.interfaces.IEmpty
import com.bbva.utilitieslib.utils.Json

const val DEFAULT_VERSION = "1.0.0"

data class IsoField(
    val version: String = DEFAULT_VERSION,
    val fields: List<Field> = listOf()
): IEmpty {

    override fun isEmpty() = (version == DEFAULT_VERSION && fields.isEmpty())

    companion object{

        fun fromJson(fileName: String): IsoField =
            Json.fromJson(fileName, IsoField::class.java)
    }
}
