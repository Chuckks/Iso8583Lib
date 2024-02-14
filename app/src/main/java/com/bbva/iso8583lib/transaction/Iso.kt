package com.bbva.iso8583lib.transaction

import android.util.Log
import com.bbva.devicelib.physical.IHsm
import com.bbva.iso8583lib.fake.Hsm
import com.bbva.iso8583lib.interfaces.IOperation
import com.bbva.iso8583lib.interfaces.ITransaccionIso
import com.bbva.iso8583lib.module.AbstractTransactionNew
import com.bbva.iso8583lib.module.EReason
import com.bbva.iso8583lib.module.Iso8583
import com.bbva.iso8583lib.transaction.enums.EHostError
import com.bbva.iso8583lib.transaction.enums.EOperation
import com.bbva.iso8583lib.utils.Constant
import com.bbva.iso8583lib.utils.DateTime
import com.bbva.utilitieslib.extensions.toHexString
import com.bbva.utilitieslib.extensions.toHexaAscii
import com.bbva.utilitieslib.extensions.toHexaBytes
import com.bbva.utilitieslib.extensions.toHexaString

private const val ERROR_CODE_SUCCESS = "00"

private val TAG = Constant.ISO_PRFIX + Iso::class.java.simpleName

open class Iso: AbstractTransactionNew, ITransaccionIso, IOperation {

    constructor() : this(EOperation.NONE)

    constructor(operData: EOperation) : super(Iso8583.IsoPackage) {
        this.operData = operData
    }

    private var operData: EOperation
    private val hsm: Hsm = Hsm()

    override suspend fun execute(inputData: IOperation.InputData) = run( inputData )

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

    override suspend fun buildOutputError(reason: EReason) =
        IOperation.OutputData(reason = reason).apply {
            message = EHostError.translateError(responseCode).value
            codeError = EHostError.toHexaAscii(responseCode)
            operation = operData.name
            result = false
            Log.i(TAG, "BuildOutputError -> [$this]")
        }

    override suspend fun buildOutput(reason: EReason) =
        IOperation.OutputData(reason = reason).apply {
            message = EHostError.translateError(responseCode).value
            codeError = EHostError.toHexaAscii(responseCode)
            operation = operData.name
            result = true
            Log.i(TAG, "BuildOutput -> [$this]")
        }

    private fun checkResponseCode(): Boolean{
        if (responseCode.isEmpty()) {
            Log.i(TAG, "Empty Response Code [${responseCode.toHexaString()}]")
            return false
        }

        Log.i(TAG, "ResponseCode [${responseCode.toHexaString()}]")

        return EHostError.toHexaAscii(responseCode) == ERROR_CODE_SUCCESS
    }

    override fun checkResponse(): EReason {
        unpackDefault()

        if( !checkResponseCode( ) ) //TODO Validar si es necesario
            return EReason.RESPONSE_FAIL


        return if (unpackSpecificFields( ) == EReason.UNPACK_SPECIFIC_FIELD_SUCCESS)
            EReason.RESPONSE_SUCCESS
        else
            EReason.RESPONSE_FAIL
        //TODO validar si se necesita un unpackErrorSpecificFields
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

    override suspend fun sendTransaction(request: ByteArray): EReason =
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

    override fun packSpecificFields(): EReason = EReason.PACK_SPECIFIC_FIELD_SUCCESS
    override fun unpackSpecificFields(): EReason = EReason.UNPACK_SPECIFIC_FIELD_SUCCESS

    override fun packField55() { }
    override fun packField56() { }

    override fun packField60() { }
    override fun packField63() { }

    override fun packDefault() {
        outputMsg.setField(0, operData.messageType)
        outputMsg.setField(3, operData.processingCode)
        outputMsg.setField(11, 123456)//TODO Tomar el Stan donde se guarde el dato

        val dateTime = DateTime()
        outputMsg.setField(12, dateTime.format(DateTime.EFormat.TIME).toInt()) //Hora local
        outputMsg.setField(13, dateTime.format(DateTime.EFormat.DATE).toInt()) //Fecha local

        outputMsg.setField(41, "00000001") //Ticket de Instalación
        outputMsg.setField(48, "004451761") //Datos adicionales de identificación del comercio.

        //Validar aqui el stan if( stan > 999999 ) stan = 1 y guardarlo
    }

    override fun unpackField63() { }
    override fun unpackDefault() {
        //TODO Validar campos a desempaquetar
        responseCode = inputMsg.getField( 39 )
    }

    override fun encryptRequest(request: String) = encryptRequest(request.toByteArray())

    override fun encryptRequest(request: ByteArray): ByteArray =
        hsm.encrypt(Hsm.EStoreIndex.DATA.ordinal, IHsm.EEncryptMode.CBC, request)

    override fun decryptResponse(response: String) = decryptResponse(response.toByteArray())

    override fun decryptResponse(response: ByteArray): ByteArray =
        hsm.decrypt(Hsm.EStoreIndex.DATA.ordinal, IHsm.EEncryptMode.CBC, response)

    override fun printTransaction() { }
    override fun printTransactionError() { }

    override suspend fun saveTransaction() { }
    override suspend fun saveTransactionError() = saveTransaction()
}