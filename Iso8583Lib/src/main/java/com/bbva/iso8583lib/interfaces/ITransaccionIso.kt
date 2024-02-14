package com.bbva.iso8583lib.interfaces

import android.util.Log
import com.bbva.iso8583lib.module.EReason
import com.bbva.iso8583lib.utils.Constant

private var TAG = Constant.ISO_PRFIX + ITransaccionIso::class.java.simpleName

interface ITransaccionIso {

    suspend fun processData(): EReason
    suspend fun processError(): EReason

    suspend fun buildOutputError( reason: EReason = EReason.NONE ): IOperation.OutputData
    suspend fun buildOutput( reason: EReason = EReason.NONE ): IOperation.OutputData

    fun checkResponse( ): EReason
    fun prepare() = EReason.PREPARE_SUCCESS

    suspend fun precondition() = EReason.PRECONDITION_SUCCESS
    suspend fun initialize( inputData: IOperation.InputData ) = EReason.INIT_SUCCESS

    fun serverConnect( ): EReason
    fun serverDisconnect( ): EReason

    suspend fun createRequest(): ByteArray
    suspend fun sendTransaction(request: ByteArray): EReason
    suspend fun receiveResponse(): EReason

    //Implements Package
    fun packSpecificFields(): EReason
    fun unpackSpecificFields(): EReason

    fun packDefault( )
    fun packField55()
    fun packField56()
    fun packField60()
    fun packField63()

    fun unpackDefault()
    fun unpackField63()

    //Implement Encrypt Request
    fun encryptRequest(request: ByteArray ): ByteArray
    fun encryptRequest(request: String ): ByteArray

    //Implement Decrypt Request
    fun decryptResponse(response: ByteArray ): ByteArray
    fun decryptResponse(response: String ): ByteArray

    fun printTransaction()
    fun printTransactionError()

    suspend fun saveTransaction()
    suspend fun saveTransactionError()

    suspend fun run(inputData: IOperation.InputData): IOperation.OutputData {
        var reason = initialize(inputData)
        Log.i( "$TAG - Run", "Initialize REASON -> [$reason]")
        if( reason != EReason.INIT_SUCCESS )
            return buildOutputError( reason )

        reason = prepare()
        Log.i( "$TAG - Run", "Prepare REASON -> [$reason]")
        if( reason != EReason.PREPARE_SUCCESS )
            return buildOutputError( reason )

        reason = precondition()
        Log.i("$TAG - Run", "Precondition REASON -> [$reason]")
        if (reason != EReason.PRECONDITION_SUCCESS)
            return buildOutputError(reason)

        return try {

            reason = serverConnect()
            Log.i( "$TAG - Run", "ServerConnect REASON -> [$reason]")
            if (reason != EReason.SERVER_CONNECT_SUCCESS)
                return buildOutputError(reason)

            reason = sendTransaction( createRequest() )
            Log.i( "$TAG - Run", "SendTransaction REASON -> [$reason]")
            if( reason != EReason.SEND_TX_SUCCESS )
                return buildOutputError( reason )

            reason = receiveResponse()
            Log.i( "$TAG - Run", "ReceiveResponse REASON -> [$reason]")
            if( reason != EReason.RECEIVE_TX_SUCCESS )
                return buildOutputError( reason )

            reason = serverDisconnect()
            Log.i( "$TAG - Run", "ServerDisconnect REASON -> [$reason]")
            if( reason != EReason.SERVER_DISCONNECT_SUCCESS )
                return buildOutputError( reason )

            reason = checkResponse()
            if ( reason == EReason.RESPONSE_SUCCESS ){
                reason = processData()
                Log.i( "$TAG - Run", "ProcessData REASON -> [$reason]")
                buildOutput( reason )
            }else{
                reason = processError()
                Log.i( "$TAG - Run", "ProcessError REASON -> [$reason]")
                buildOutputError( reason )
            }

        }catch ( e: Exception) {
            Log.e("$TAG - Run", "Exception -> IO Exception [${e.message}]")
            buildOutputError( EReason.IO_EXCEPTION )
        }
    }
}
