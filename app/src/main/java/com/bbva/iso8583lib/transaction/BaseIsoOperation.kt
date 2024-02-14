package com.bbva.iso8583lib.transaction


import android.util.Log
import com.bbva.iso8583lib.comms.ServerData
import com.bbva.iso8583lib.comms.SocketServer
import com.bbva.iso8583lib.iso.UnpackerIso
import com.bbva.iso8583lib.module.AbstractTransaction
import com.bbva.iso8583lib.module.EReason
import com.bbva.iso8583lib.transaction.enums.EOperation
import com.bbva.iso8583lib.utils.Constant
import com.bbva.iso8583lib.utils.DateTime
import com.bbva.utilitieslib.extensions.toBcdToDecimal

private val TAG = Constant.ISO_PRFIX + BaseIsoOperation::class.java

abstract class BaseIsoOperation : AbstractTransaction {

    constructor(packageIso: UnpackerIso) : super(packageIso)

    constructor(packageIso: UnpackerIso, operData: EOperation) : super(packageIso) {
        this.operData = operData
    }

    protected var responseCode: Int = -1
    protected var server: ServerData = ServerData() //TODO inicializar con los datos Globales desde el core
    protected var socket: SocketServer = SocketServer()
    protected var operData: EOperation = EOperation.NONE

    override suspend fun processData(): EReason {
        saveTransaction()
        printTransaction()
        return EReason.OPERATION_SUCCESS
    }

    override suspend fun processError(): EReason {
        saveTransactionError()
        printTransactionError()
        return EReason.OPERATION_FAIL
    }

    override fun serverConnect(): EReason =
        if(socket.connect())
            EReason.SERVER_CONNECT_SUCCESS
        else
            EReason.SERVER_CONNECT_FAIL


    override fun serverDisconnect(): EReason =
        if(socket.disconnect())
            EReason.SERVER_DISCONNECT_SUCCESS
        else
            EReason.SERVER_DISCONNECT_FAIL

    override suspend fun createRequest(): ByteArray {
        outputMsg.clean()
        packDefault()
        packField55()
        packField56()
        packField60()
        packField63()
        packSpecificFields()
        return outputMsg.pack()
    }

    override suspend fun sendTransaction(request: ByteArray) =
        if (socket.send(request))
            EReason.SEND_TX_SUCCESS
        else
            EReason.SEND_TX_FAIL


    override suspend fun receiveResponse(): EReason {
        val recivie = socket.receive()
        if (recivie.isEmpty()) {
            Log.i(TAG, "Data Receive is Empty")
            EReason.RECEIVE_TX_FAIL
        }

        inputMsg.unpack(recivie)
        return EReason.RECEIVE_TX_SUCCESS
    }

    override fun packDefault() {
        outputMsg.setField(0, operData.messageType)
        outputMsg.setField(3, operData.processingCode)
        outputMsg.setField(11, 123456)//TODO Tomar el Stan donde se guarde el dato

        val dateTime = DateTime()
        outputMsg.setField(12, dateTime.format(DateTime.EFormat.TIME)) //Hora local
        outputMsg.setField(13, dateTime.format(DateTime.EFormat.DATE)) //Fecha local

        outputMsg.setField(41, 1) //Ticket de Instalación
        outputMsg.setField(48, 4451761) //Datos adicionales de identificación del comercio.

        //Validar aqui el stan if( stan > 999999 ) stan = 1 y guardarlo
    }

    override fun unpackDefault() {
        val date = inputMsg.getField(12)
        val response = inputMsg.getField( 39 )
        if (response.isEmpty())
            responseCode = -1
        else
            responseCode = response.toBcdToDecimal(2)

        Log.i(TAG, "ResponseCode [$responseCode]")
        //TODO Validar campos a desempaquetar

    }

    override fun checkResponse(): EReason {
        inputMsg.unpack(socket.receive())
        unpackDefault()

        if( !checkResponseCode( ) ) //TODO Validar si es necesario
            return EReason.RESPONSE_FAIL

        return if (unpackSpecificFields( ) == EReason.UNPACK_SPECIFIC_FIELD_SUCCESS)
            EReason.RESPONSE_SUCCESS
        else
            EReason.RESPONSE_FAIL
    }

    private fun checkResponseCode(): Boolean{
        val result: Boolean = responseCode <= 1;
        Log.i(TAG, "checkResponseCode RESULT [$responseCode]")
        return result;
    }

    protected abstract fun printTransaction()
    protected abstract fun printTransactionError()

    protected abstract suspend fun saveTransaction()
    protected abstract suspend fun saveTransactionError()

    //Implement Encrypt Request
    override fun encryptRequest(request: ByteArray ): ByteArray = ByteArray(0)
    override fun encryptRequest(request: String ): ByteArray = encryptRequest(request.toByteArray())

    //Implement Decrypt Request
    override fun decryptResponse(response: ByteArray ): ByteArray = ByteArray(0)
    override fun decryptResponse(response: String ): ByteArray = decryptResponse(response.toByteArray())

}