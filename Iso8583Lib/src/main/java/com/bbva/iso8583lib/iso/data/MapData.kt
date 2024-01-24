package com.bbva.iso8583lib.iso.data

import com.bbva.iso8583lib.interfaces.IContenible

private val DEFAULT_TYPE = EFormat.UNKNOW
private const val DEFAULT_LENGTH = -1
private const val DEFAULT_POSITION = -1

data class MapData(var type: EFormat = DEFAULT_TYPE,
                   var length: Int = DEFAULT_LENGTH,
                   var position: Int = DEFAULT_POSITION): IContenible {

    override fun clean() {
        type = DEFAULT_TYPE
        length = DEFAULT_LENGTH
        position = DEFAULT_POSITION
    }

    override fun isEmpty() = (type == DEFAULT_TYPE && length == DEFAULT_LENGTH && position == DEFAULT_POSITION)

    fun setMap(type: EFormat, length: Int, position: Int): MapData {
        this.type = type
        this.length = length
        this.position = position
        return this
    }
}