package com.bbva.iso8583lib.iso

import android.util.Log
import com.bbva.iso8583lib.interfaces.IContenible
import com.bbva.iso8583lib.iso.data.EFormat
import com.bbva.iso8583lib.iso.data.MapData
import com.bbva.iso8583lib.utils.Constant

private const val DEFAULT_MTI: Boolean = true
private const val BITMAP_SIZE: Int = 8
private const val DEFAULT_HEADER_SIZE: Int = 0
private const val MAX_FRAME_SIZE: Int = 4096
private const val DEFAULT_MAPPER_SIZE = 65

private val TAG = Constant.ISO_PRFIX + Message::class.java.simpleName

open class Message(
    protected var header: ByteArray = ByteArray(0),
    protected var headerSize: Int = DEFAULT_HEADER_SIZE,
    protected var frame: ByteArray = ByteArray(MAX_FRAME_SIZE),
    protected var bitmap: Bitmap = Bitmap(BITMAP_SIZE),
    protected var mapper: MutableList<MapData> = MutableList(DEFAULT_MAPPER_SIZE){MapData()},
    protected var packageData: UnpackerIso = UnpackerIso(),
    protected var mtiAvailable: Boolean = DEFAULT_MTI): IContenible {

    override fun clean() {
        header = ByteArray(header.size)
        frame = ByteArray(frame.size)

        bitmap.clean()
        mapper.clear()

        headerSize = 0
    }

    override fun isEmpty(): Boolean {
        return frame.isEmpty() && bitmap.isEmpty() && mapper.isEmpty()
    }

    fun getFormat(nField: Byte): EFormat {
        return packageData.getItem(nField.toInt()).getFormat()
    }

    fun getSize(nField: Byte): Int {
        return packageData.getItem(nField.toInt()).size
    }

    fun isEmpty(nField: Byte): Boolean {
        if(mapper.isNullOrEmpty()) {
            Log.i(TAG, "Mapper is Empty")
            return true
        }

        return mapper[nField.toInt()].isEmpty()
    }


    fun getValue(nField: Byte): ByteArray {
        if (mapper.isNullOrEmpty())
            throw ArrayIndexOutOfBoundsException("Mapper is Empty or Index Out Bound")

        val length = mapper[nField.toInt()].length
        val position = mapper[nField.toInt()].position

        if (length < 0 || position < 0)
            throw ExceptionInInitializerError("nField [$nField]")

        return frame.copyOfRange(position, position + length)
    }
}

