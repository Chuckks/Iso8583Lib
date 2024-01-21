package com.bbva.iso8583lib.iso

import android.util.Log
import com.bbva.iso8583lib.interfaces.IEmpty
import com.bbva.iso8583lib.iso.data.EFormat
import com.bbva.iso8583lib.iso.data.MapData
import com.bbva.iso8583lib.utils.Constant
import com.bbva.utilitieslib.extensions.getDigitCount
import com.bbva.utilitieslib.utils.Convert

private val TAG = Constant.ISO_PRFIX + OutputMessage::class.java.simpleName

class OutputMessage(packagerIso: UnpackerIso, mtiAvailable: Boolean, frameSize: Int) :
    Message(packageData = packagerIso, mtiAvailable = mtiAvailable, frame = ByteArray(frameSize)),
    IEmpty {

    constructor(unpackerIso: UnpackerIso) :
            this(packagerIso = unpackerIso, mtiAvailable = false, frameSize = 0)

    override fun isEmpty() = (frame.isEmpty() && bitmap.isEmpty() && mapper.isEmpty())

    fun pack(): ByteArray {
        if (!packageData.unpack)
            throw ExceptionInInitializerError("PackageData")

        if (this.frame.isEmpty())
            throw ExceptionInInitializerError( "Not set any fields")

        var result = ByteArray(getFragmentLen())

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

            var mti = ByteArray(mapDat.length)
            mti += frame.copyOfRange(mapDat.position, mapDat.position + mapDat.length) //TODO Revisar si esta sacando bien los datos

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
                bitmap.setBitOn(bit.toByte())
                var field = ByteArray(mapDat.length)
                field += frame.copyOfRange(mapDat.position, mapDat.position + mapDat.length)
                val nFieldLog = "F%02d".format(nField)
                Log.i(TAG, "$nFieldLog FIELD [${field.contentToString()}] SIZE[${field.size}")
                result += field
            }

            nField++
        }
        //TODO validar como agregar el bitmap al result
        //result.copyTo(bitmap.value, positionBitmap)
        //result.copytwrite(positionBitmap, bitmap.value)
        Log.i(TAG, "BITMAP [${bitmap.value.contentToString()}  SIZE[${bitmap.value.size}]")

        return result
    }


    private fun getFragmentLen(): Int{
        var length: Int
        var totalLength = header.size + bitmap.getSize()

        for (nField in 0 until this.mapper.size) {
            length = this.mapper[nField].length
            if (length > 0) {
                totalLength += length
            }
        }
        return totalLength
    }

    fun setField(nField: Int, value: ByteArray) {
        val size = packageData.getItem(nField).size
        val format = packageData.getItem(nField).getFormat()

        val count = value.size.coerceAtMost(size)  // Usamos size del ByteArray
        val position = this.frame.size
        val variable = addLength(format, count, size)

        mapper[nField] = MapData(format, count + variable, position)

        frame += (value.sliceArray(0 until count))  // Añadimos solo los primeros 'count' elementos
    }

    fun setField(nField: Int, value: String){
        setField(nField, value.toByteArray())
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
        var variable: Int

        when(format){
            EFormat.VAR_CHAR,
            EFormat.VAR_NUMERIC -> {
                variable = size.getDigitCount()

                if (variable and 0x01 != 0)
                    ++variable

                variable /= 2
                frame += Convert.toDecimalToBcd(variable, length)
            }

            EFormat.FIX_CHAR,
            EFormat.FIX_NUMERIC -> {
                variable = 0
             }

            else -> {
                throw IllegalArgumentException("Format [$format] Wrong")
                return 0
            }
        }
        return variable
    }

}