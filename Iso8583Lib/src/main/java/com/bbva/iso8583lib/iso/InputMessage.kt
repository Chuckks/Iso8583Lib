package com.bbva.iso8583lib.iso

import android.util.Log
import com.bbva.iso8583lib.interfaces.IEmpty
import com.bbva.iso8583lib.iso.data.EFormat
import com.bbva.iso8583lib.iso.data.MapData
import com.bbva.iso8583lib.utils.Constant
import com.bbva.utilitieslib.extensions.getDigitCount
import com.bbva.utilitieslib.extensions.readBbcPair
import com.bbva.utilitieslib.extensions.readBcd
import com.bbva.utilitieslib.extensions.toHexaString

private val TAG = Constant.ISO_PRFIX + InputMessage::class.java.simpleName

class InputMessage(packagerIso: UnpackerIso, mtiAvailable: Boolean, frameSize: Int) :
    Message(packageData = packagerIso, mtiAvailable = mtiAvailable, frame = ByteArray(frameSize)),
    IEmpty {

    constructor(unpackerIso: UnpackerIso) :
            this(packagerIso = unpackerIso, mtiAvailable = false, frameSize = 0)

    override fun isEmpty() = (frame.isEmpty() && bitmap.isEmpty() && mapper.isEmpty())

    fun unpack(frame: ByteArray) {
        if (!packageData.unpack)
            throw IllegalArgumentException("PackageData is Empty")

        if (frame.isEmpty())
            throw IllegalArgumentException("Frame [${frame.size}]")

        mapper.clear()
        this.frame = frame

        val mapper = MapData()
        var nField = 0
        var position = 0

        if (headerSize > 0) {
            header = this.frame.copyOfRange(position, headerSize)
            position += headerSize
            Log.i(TAG, "HEADER [${header.toHexaString()}]")
        }

        var length: Int
        if (mtiAvailable) {
            //@MESSAGE TYPE
            val field = packageData.getItem(nField)
            length = field.size / 2
            mapper.setMap(field.getFormat(), length, position)

            this.mapper[nField] = mapper
            Log.i(TAG, "MTI [${frame.copyOfRange(position, length).toHexaString()}")
            position += length
        }
        ++nField

        //@BITMAP
        bitmap = Bitmap(this.frame.copyOfRange(position, packageData.getItem(nField).size))
        length = bitmap.getSize()

        mapper.setMap(packageData.getItem(nField).getFormat(), length, position)
        this.mapper[nField] = mapper
        Log.i(TAG, "BITMAP [${bitmap.value.toHexaString()}]")

        for (bit in 1 until packageData.getCounter()) {
            if (bitmap.isBitOn(bit.toByte())) {
                val format = packageData.getItem(nField).getFormat()

                var size = packageData.getItem(nField).size
                var digitCount = size.getDigitCount()

                when (format) {
                    EFormat.VAR_CHAR -> {
                        if (digitCount and 0x01 != 0) {
                            digitCount++
                        }
                        val (count, len) = frame.readBbcPair(position, digitCount)
                        position += count
                        length = len

                        if (size < length)
                            throw IndexOutOfBoundsException("field [$bit] size[$size] < length[$length]")
                    }

                    EFormat.VAR_NUMERIC -> {
                        if (digitCount and 0x01 != 0) {
                            ++digitCount
                        }

                        if (size and 0x01 != 0) {
                            ++size
                        }

                        val (count, len) = frame.readBbcPair(position, digitCount)
                        position += count
                        length = len
                        length /= 2

                        if (size < length) {
                            throw IndexOutOfBoundsException("field [$bit] size[$size] < length[$length]")
                        }
                    }

                    EFormat.FIX_NUMERIC -> {
                        if (size and 0x01 != 0) {
                            size++
                        }
                        length = (size / 2)
                    }

                    EFormat.FIX_CHAR -> {
                        length = size
                    }

                    else -> {
                        throw Exception("format[$format] wrong")
                    }

                }
                mapper.setMap(format, length, position)
                this.mapper[nField] = mapper
                Log.i(
                    TAG,
                    "Field[$nField] - Frame[${
                        frame.copyOfRange(
                            position,
                            frame.size
                        )
                    }] - Lenght[$length]"
                )
                position += length
            }
            nField++
        }
    }

    fun getNumField(nField: Int): Int {
        var field: Int
        val mapData = validateFieldFormat(nField, true)
        field = frame.readBcd(mapData.position, mapData.length * 2)
        return field
    }

    fun getField(nField: Int): ByteArray {
        val mapData = validateFieldFormat(nField, false)
        return frame.copyOfRange(mapData.position, mapData.position + mapData.length)
    }

    fun getLength(nField: Int): Int =
        validateFieldFormat(nField, false).length

    private fun validateFieldFormat(nField: Int, numeric: Boolean): MapData {
        val mapData = mapper[nField]

        if (mapData.isEmpty()) {
            throw ExceptionInInitializerError("Mapper position (nField [$nField])")
        }

        val format = packageData.getItem(nField).getFormat()

        if (numeric) {
            if (format != EFormat.VAR_NUMERIC && format != EFormat.FIX_NUMERIC) {
                throw UnsupportedOperationException(" Format [${format.name}] -> nField [$nField]")
            }
        } else if (format != EFormat.VAR_CHAR && format != EFormat.FIX_CHAR) {
            throw UnsupportedOperationException(" Format [${format.name}] -> nField [$nField]")
        }
        return mapData
    }
}