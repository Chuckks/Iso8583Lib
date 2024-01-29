package com.bbva.iso8583lib.transaction.interfaces

import android.util.Log
import com.bbva.iso8583lib.transaction.enums.EReason

interface ITransaction {
    suspend fun processData(): EReason
    suspend fun processError(): EReason

    suspend fun buildOutputError( reason: EReason = EReason.NONE ): IOperation.OutputData
    suspend fun buildOutput( reason: EReason = EReason.NONE ): IOperation.OutputData

    fun checkResponse( ): EReason
    fun prepare() = EReason.PREPARE_SUCCESS
    suspend fun precondition() = EReason.PRECONDITION_SUCCESS
    fun loadKeys( ): EReason
    suspend fun initialize( inputData: IOperation.InputData ) = EReason.INIT_SUCCESS

    fun serverConnect( ): EReason
    fun serverDisconnect( ): EReason
    suspend fun createRequest(): ByteArray
    suspend fun sendTransaction(request: ByteArray): EReason
    suspend fun receiveResponse(): EReason
    suspend fun run(inputData: IOperation.InputData): IOperation.OutputData {
        var reason = initialize(inputData)
        Log.i( "run", "Initialize REASON -> [$reason]")
        if( reason != EReason.INIT_SUCCESS )
            return buildOutputError( reason )

        reason = prepare()
        Log.i( "run", "prepare REASON -> [$reason]")
        if( reason != EReason.PREPARE_SUCCESS )
            return buildOutputError( reason )

        reason = precondition()
        Log.i("run", "precondition REASON -> [$reason]")
        if (reason != EReason.PRECONDITION_SUCCESS)
            return buildOutputError(reason)

        reason = loadKeys()
        Log.i( "run", "loadKeys REASON -> [$reason]")
        if (reason != EReason.LOAD_KEYS)
            return buildOutputError(reason)

        return try {

            reason = serverConnect()
            Log.i( "run", "serverConnect REASON -> [$reason]")
            if (reason != EReason.SERVER_CONNECT_SUCCESS)
                return buildOutputError(reason)

            reason = sendTransaction( createRequest() )
            Log.i( "run", "sendTransaction REASON -> [$reason]")
            if( reason != EReason.SEND_TX_SUCCESS )
                return buildOutputError( reason )

            reason = receiveResponse()
            Log.i( "run", "receiveResponse REASON -> [$reason]")
            if( reason != EReason.RECEIVE_TX_SUCCESS )
                return buildOutputError( reason )

            reason = serverDisconnect()
            Log.i( "run", "serverDisconnect REASON -> [$reason]")
            if( reason != EReason.SERVER_DISCONNECT_SUCCESS )
                return buildOutputError( reason )

            reason = checkResponse()
            if ( reason == EReason.RESPONSE_SUCCESS ){
                reason = processData()
                Log.i( "run", "processData REASON -> [$reason]")
                buildOutput( reason )
            }else{
                reason = processError()
                Log.i( "run", "processError REASON -> [$reason]")
                buildOutputError( reason )
            }

        }catch ( e: Exception) {
            Log.e("run", "Exception -> IO Exception [${e.message}]")
            buildOutput( EReason.IO_EXCEPTION )
        }
    }
}
