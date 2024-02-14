package com.bbva.iso8583lib.iso

import android.util.Log
import com.bbva.iso8583lib.interfaces.IContenible
import com.bbva.iso8583lib.iso.data.EFormat
import com.bbva.iso8583lib.iso.data.MapData
import com.bbva.iso8583lib.utils.Constant
import com.bbva.utilitieslib.extensions.toHexaBytes

private const val DEFAULT_MTI = true
private const val BITMAP_SIZE = 8
private const val DEFAULT_HEADER = "6000220000"
private const val DEFAULT_HEADER_SIZE = 7
private const val MAX_FRAME_SIZE = 0
private const val DEFAULT_MAPPER_SIZE = 65

private val TAG = Constant.ISO_PRFIX + Message::class.java.simpleName

open class Message: IContenible {

    constructor(packageIso: UnpackerIso) {
        packageData = packageIso
    }

    constructor(packageIso: UnpackerIso, mTIAvailable: Boolean) {
        packageData = packageIso
        mtiAvailable = mTIAvailable
    }

    constructor(packageIso: UnpackerIso, mTIAvailable: Boolean, frameSize: Int) {
        packageData = packageIso
        mtiAvailable = mTIAvailable
        frame = ByteArray(frameSize)
    }

    constructor(mTIAvailable: Boolean) {
        mtiAvailable = mTIAvailable
    }

    constructor(mTIAvailable: Boolean, frameSize: Int) {
        mtiAvailable = mTIAvailable
        frame = ByteArray(frameSize)
    }

    var header: ByteArray = DEFAULT_HEADER.toHexaBytes()
    protected var headerSize: Int = DEFAULT_HEADER_SIZE
    protected var frame: ByteArray = ByteArray(MAX_FRAME_SIZE)
    protected var bitmap: Bitmap = Bitmap(BITMAP_SIZE)
    protected var mapper: MutableList<MapData> = MutableList(DEFAULT_MAPPER_SIZE){MapData()}
    protected var packageData: UnpackerIso = UnpackerIso()
    protected var mtiAvailable: Boolean = DEFAULT_MTI

    override fun clean() {
       // frame = ByteArray(0)
       // bitmap.clean()
    }

    override fun isEmpty(): Boolean {
        return frame.isEmpty() && bitmap.isEmpty() && mapper.isEmpty()
    }

    fun getFormat(nField: Int): EFormat {
        return packageData.getItem(nField).getFormat()
    }

    fun getSize(nField: Int): Int {
        return packageData.getItem(nField).size
    }

    fun isEmpty(nField: Int): Boolean {
        if(mapper.isNullOrEmpty()) {
            Log.i(TAG, "Mapper is Empty")
            return true
        }

        return mapper[nField].isEmpty()
    }


    fun getValue(nField: Int): ByteArray {
        if (mapper.isNullOrEmpty())
            throw ArrayIndexOutOfBoundsException("Mapper is Empty or Index Out Bound")

        val length = mapper[nField].length
        val position = mapper[nField].position

        if (length < 0 || position < 0)
            throw ExceptionInInitializerError("nField [$nField]")

        return frame.copyOfRange(position, position + length)
    }
}

