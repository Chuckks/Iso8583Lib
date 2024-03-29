package com.bbva.iso8583lib.iso.data

import com.bbva.utilitieslib.interfaces.IEmpty

private const val DEFAULT_FIELD = 0
private const val DEFAULT_SIZE = 0
private const val DEFAULT_NAME = ""
private const val DEFAULT_FORMAT = "UNKNOW"

//PackagerData
data class Field (
    val field: Int = DEFAULT_FIELD,
    val name: String = DEFAULT_NAME,
    val format: String = DEFAULT_FORMAT,
    val size: Int = DEFAULT_SIZE
): IEmpty {
    override fun isEmpty() = (field == DEFAULT_FIELD && name == DEFAULT_NAME
            && size == DEFAULT_SIZE && format == DEFAULT_FORMAT)

    fun checkField(field: Int) = field == this.field

    fun getFormat() = EFormat.translateFormat(format)
}

enum class EFormat(val value: String) {
    UNKNOW("UNKNOW"),
    FIX_CHAR("FIX_CHAR"),
    FIX_NUMERIC("FIX_NUMERIC"),
    VAR_CHAR("VAR_CHAR"),
    VAR_NUMERIC("VAR_NUMERIC");

    companion object {

        public fun translateFormat(value: String): EFormat =
            when (value) {
                "FIX_CHAR"    -> FIX_CHAR
                "FIX_NUMERIC" -> FIX_NUMERIC
                "VAR_CHAR"    -> VAR_CHAR
                "VAR_NUMERIC" -> VAR_NUMERIC
                else          -> throw IllegalArgumentException("Illegal Format [$value]")
        }
    }
}
