package com.bbva.iso8583lib.module

import android.annotation.SuppressLint
import android.content.Context
import com.bbva.iso8583lib.interfaces.IEmpty

object Iso8583 {

    @SuppressLint("StaticFieldLeak")
    private var configData = ConfigData()

    var init = false
        private set

    val context: Context
        get() = configData.context!!

    val isoFileName: String
        get() = configData.isoFileName

    private val DEFAULT_CONTEXT = null

    data class ConfigData(
        val context: Context? = DEFAULT_CONTEXT,
        val isoFileName: String = ""
    ): IEmpty {
        override fun isEmpty() = (context == DEFAULT_CONTEXT && isoFileName.isEmpty())
    }

    fun initModule(configData: ConfigData) {
        this.configData = configData
        init = true
    }
}