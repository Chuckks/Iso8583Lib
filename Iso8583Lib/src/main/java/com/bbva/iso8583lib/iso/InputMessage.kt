package com.bbva.iso8583lib.iso

import android.util.Log
import com.bbva.iso8583lib.interfaces.IEmpty
import com.bbva.iso8583lib.iso.data.EFormat
import com.bbva.iso8583lib.iso.data.MapData
import com.bbva.iso8583lib.utils.Constant
import com.bbva.utilitieslib.extensions.getDigitCount
import com.bbva.utilitieslib.extensions.readBbcPair
import com.bbva.utilitieslib.extensions.toHexaString

private const val DEFAULT_FRAME_SIZE = 0
private const val DEFAULT_MTI = true

private val TAG = Constant.ISO_PRFIX + InputMessage::class.java.simpleName

class InputMessage(packagerIso: UnpackerIso, mtiAvailable: Boolean, frameSize: Int) :
    Message(packageData = packagerIso, mtiAvailable = mtiAvailable, frame = ByteArray(frameSize), headerSize = 7),
    IEmpty {

    constructor(unpackerIso: UnpackerIso) :
            this(packagerIso = unpackerIso, mtiAvailable = DEFAULT_MTI, frameSize = DEFAULT_FRAME_SIZE)

    override fun isEmpty() = (frame.isEmpty() && bitmap.isEmpty() && mapper.isEmpty())

    fun unpack(frame: ByteArray) {
        if (!packageData.unpack)
            throw IllegalArgumentException("PackageData is Empty")

        if (frame.isEmpty())
            throw IllegalArgumentException("Frame [${frame.size}]")

        this.frame = frame

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

            this.mapper[nField] = MapData().setMap(field.getFormat(), length, position)
            Log.i(TAG, "MTI [${frame.copyOfRange(position, position + length).toHexaString()}")
            position += length
        }
        ++nField

        //@BITMAP
        bitmap = Bitmap(this.frame.copyOfRange(position, position + packageData.getItem(nField).size))
        length = bitmap.getSize()

        this.mapper[nField] = MapData().setMap(packageData.getItem(nField).getFormat(), length, position)

        Log.i(TAG, "BITMAP [${bitmap.value.toHexaString()}]")
        position += length

        for (bit in 1 until packageData.getCounter()) {
            if (bitmap.isBitOn(bit)) {
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
                            throw IndexOutOfBoundsException("field [$bit] size[$size] < length[$length] FRAME[${frame.toHexaString()}")
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
                            throw IndexOutOfBoundsException("field [$bit] size[$size] < length[$length] FRAME[${frame.toHexaString()}")
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

                this.mapper[nField] = MapData().setMap(format, length, position)
                Log.i(TAG,"Field[$nField] - Field[${frame.copyOfRange(position, position + length).toHexaString()}] - Lenght[$length]")
                position += length
            }
            nField++
        }
    }

    fun getField(nField: Int): ByteArray {
        val mapData = mapper[nField]

        if (mapData.isEmpty()) {
            Log.i(TAG, "Mapper position nField [$nField] is Empty" )
            return ByteArray(0)
        }

        return frame.copyOfRange(mapData.position, mapData.position + mapData.length)
    }
}