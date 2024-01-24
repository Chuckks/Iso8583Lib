package com.bbva.iso8583lib.iso

import android.util.Log
import com.bbva.iso8583lib.interfaces.IEmpty
import com.bbva.iso8583lib.iso.data.EFormat
import com.bbva.iso8583lib.iso.data.MapData
import com.bbva.iso8583lib.utils.Constant
import com.bbva.utilitieslib.extensions.getDigitCount
import com.bbva.utilitieslib.utils.Convert

private val TAG = Constant.ISO_PRFIX + OutputMessage::class.java.simpleName

private const val DEFAULT_FRAME_SIZE = 0
private const val DEFAULT_MTI = true

class OutputMessage(packagerIso: UnpackerIso, mtiAvailable: Boolean, frameSize: Int) :
    Message(packageData = packagerIso, mtiAvailable = mtiAvailable, frame = ByteArray(frameSize)),
    IEmpty {

    constructor(unpackerIso: UnpackerIso) :
            this(packagerIso = unpackerIso, mtiAvailable = DEFAULT_MTI, frameSize = DEFAULT_FRAME_SIZE)

    override fun isEmpty() = (frame.isEmpty() && bitmap.isEmpty() && mapper.isEmpty())

    fun pack(): ByteArray {
        if (!packageData.unpack)
            throw ExceptionInInitializerError("PackageData")

        if (this.frame.isEmpty())
            throw ExceptionInInitializerError( "Not set any fields")

        var result = ByteArray(0)

        if (header.isNotEmpty() && headerSize == this.header.size) {
            result += this.header
            Log.i(TAG, "HEADER [${header.contentToString()}] SIZE[${header.size}]")
        }

        var nField: Int = 0

        //@MESSAGE TYPE
        if (mtiAvailable) {
            val mapDat = mapper[nField]

            if (mapDat.isEmpty())
                throw  ExceptionInInitializerError("nField [$nField]")

            var mti = frame.copyOfRange(mapDat.position, mapDat.position + mapDat.length)
            Log.i(TAG, "MTI  [${mti.contentToString()}] SIZE [${mti.size}]")
            result += mti
        }
        nField++

        //@BITMAP
        bitmap.clean()
        val positionBitmap = result.size
        result += bitmap.value

        for (bit in 1 until packageData.getCounter()) {
            val mapDat = mapper[nField]

            if (mapDat.isNotEmpty()) {
                bitmap.setBitOn(bit)
                var field = ByteArray(0)
                field += frame.copyOfRange(mapDat.position, mapDat.position + mapDat.length)

                val nFieldLog = "F%02d".format(nField)
                Log.i(TAG, "$nFieldLog FIELD [${field}] SIZE[${field.size}")
                result += field
            }

            nField++
        }
        bitmap.value.copyInto(result, destinationOffset = positionBitmap)
        Log.i(TAG, "BITMAP [${Convert.toBcdToHexa(bitmap.value)}  SIZE[${bitmap.value.size}]")
        return result
    }

    fun setField(nField: Int, value: String){
        setField(nField, value.toByteArray())
    }

    fun setField(nField: Int, value: ByteArray) {
        val size = packageData.getItem(nField).size
        val format = packageData.getItem(nField).getFormat()
        var length = value.size

        if( length > size ) {
            Log.i(TAG,"Field [$nField] lenCopy [$length] > sizeField [$size]")
            length = size
        }


        val position = this.frame.size
        val variable = addLength(format, length, size)

        mapper[nField] = MapData(format, variable + length, position)
        frame += (value.sliceArray(0 until length))
    }

    fun setField(nField: Int, value: Int){
        var aux = StringBuilder()
        val field = packageData.getItem(nField)
        var size = field.size

        if ((size and 0x01 ) != 0)
            ++size

        val format = field.getFormat()
        val length = aux.append("%0${size}d".format(value)).length

        val position = frame.size
        val variable = addLength(format, length, size)
        size /= 2

        mapper[nField] = MapData(format, size + variable, position)

       frame += Convert.toAsciiToBcd(aux.toString().toByteArray())
    }

    private fun addLength(format: EFormat, length: Int, size: Int): Int{
        return when(format){
            EFormat.VAR_CHAR,
            EFormat.VAR_NUMERIC -> {
                var variable = size.getDigitCount()

                if (variable and 0x01 != 0)
                    ++variable

                variable /= 2
                frame += Convert.toDecimalToBcd(variable, length)
                variable
            }

            EFormat.FIX_CHAR,
            EFormat.FIX_NUMERIC -> { 0 }

            else -> { 0 }
        }
    }

}