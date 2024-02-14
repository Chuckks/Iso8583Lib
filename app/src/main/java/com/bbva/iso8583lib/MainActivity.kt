package com.bbva.iso8583lib

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bbva.devicelib.emv.inputData.AmountData
import com.bbva.iso8583lib.interfaces.IOperation.InputData
import com.bbva.iso8583lib.iso.data.Version
import com.bbva.iso8583lib.module.Iso8583
import com.bbva.iso8583lib.transaction.operations.Sale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val FILE_NAME_ISO = "ISO8583.json"
class MainActivity : AppCompatActivity() {

    private fun initModuleIso(){
        val configData = Iso8583.ConfigData(
            this,
            FILE_NAME_ISO,
            Version(1,0,0)
        )
        Iso8583.initModule(configData)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initModuleIso()
        CoroutineScope(Dispatchers.IO).launch {
           val result =  Sale().execute(InputData(amountData = 9050))
            result.reason
            result.result
            result.operation
        }
    }
}