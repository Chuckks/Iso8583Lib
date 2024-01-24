package com.bbva.iso8583lib.module

import android.annotation.SuppressLint
import android.content.Context
import com.bbva.iso8583lib.interfaces.IEmpty
import com.bbva.iso8583lib.iso.UnpackerIso
import com.bbva.iso8583lib.iso.data.Version

private val DEFAULT_VERSION = Version(1, 0, 0)
object Iso8583 {

    @SuppressLint("StaticFieldLeak")
    private var configData = ConfigData()
    private var IsoPkg: UnpackerIso = UnpackerIso(initVersion = DEFAULT_VERSION)

    var init = false
        private set

    val context: Context
        get() = configData.context!!

    val IsoPackage: UnpackerIso
        get() = IsoPkg

    val isoFileName: String
        get() = configData.isoFileName

    private val DEFAULT_CONTEXT = null

    data class ConfigData(
        val context: Context? = DEFAULT_CONTEXT,
        val isoFileName: String = "",
        val version: Version = DEFAULT_VERSION
    ): IEmpty {
        override fun isEmpty() = (context == DEFAULT_CONTEXT && isoFileName.isEmpty())
    }

    fun initModule(configData: ConfigData) {
        this.configData = configData
        IsoPkg.unpacker(configData.isoFileName, configData.version)
        init = true
    }
}