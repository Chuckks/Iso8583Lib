package com.bbva.iso8583lib.iso

import android.util.Log
import com.bbva.iso8583lib.interfaces.IEmpty
import com.bbva.iso8583lib.iso.data.Field
import com.bbva.iso8583lib.iso.data.IsoField
import com.bbva.iso8583lib.iso.data.Version
import com.bbva.iso8583lib.module.Iso8583
import com.bbva.iso8583lib.utils.Constant
import com.bbva.utilitieslib.extensions.getAssetToString

private const val DEFAULT_FILENAME = ""
private const val DEFAULT_MAXCOUNT = 128
private const val DEFAULT_VERSION_MINOR = 1

private val TAG = Constant.ISO_PRFIX + UnpackerIso::class.java.simpleName

class UnpackerIso(val fileName: String = DEFAULT_FILENAME, val initVersion: Version = Version(),
                                val maxCount: Int = DEFAULT_MAXCOUNT): IEmpty {

    var unpack: Boolean =  false
        private set

    private var listField: List<Field> = listOf()

    override fun isEmpty() = (initVersion.isEmpty() && fileName.isEmpty()
              && maxCount == DEFAULT_MAXCOUNT && listField.isEmpty())

    fun getCounter() = listField.size

    fun search(value: Field): Field {
        validateUnpack()

        listField.forEach { field ->
            if (field == value)
                return field
        }
        return Field()
    }

    fun unpacker(fileName: String){
        if(!unpack){
            val jsonField = IsoField.fromJson(Iso8583.context.getAssetToString(fileName))
            Log.i(TAG, "ISO8583 Version [${jsonField.version}]")
            checkVersion(Version.parser(jsonField.version))

            listField = jsonField.fields
            Log.i(TAG, "Fields [${jsonField.fields.toString()}]")
            unpack = true
        }else
            Log.i("ISO", "Already Unpacker")

    }

    fun getItem( index: Int ): Field {
        validateUnpack()
        return listField[index]
    }

    protected fun checkVersion(version: Version){
        if( initVersion.compareTo(version) == DEFAULT_VERSION_MINOR )
            throw UnsupportedOperationException("initVersion [$initVersion] > version [$version] ")
    }

    protected fun validateUnpack(){
        if( !unpack )
            throw ExceptionInInitializerError("Unpack [$fileName]")
    }
}