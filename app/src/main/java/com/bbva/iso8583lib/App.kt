package com.bbva.iso8583lib

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.bbva.iso8583lib.transaction.enums.EOperation
import com.bbva.iso8583lib.transaction.interfaces.IOperation
import com.bbva.iso8583lib.transaction.operations.Sale

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        fun getAppContext(): Context {
            return context
        }

        fun createOperation(operation: EOperation): IOperation {
            return when(operation) {
                //EOperation.SALE          ->  Sale()
                else                         -> throw Exception("Invalid Operation [${operation}]")
            }
        }
    }
}