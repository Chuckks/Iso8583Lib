package com.bbva.iso8583lib.transaction

import com.bbva.devicelib.physical.IHsm
import com.bbva.iso8583lib.fake.Hsm
import com.bbva.iso8583lib.module.EReason
import com.bbva.iso8583lib.transaction.enums.EOperation
import com.bbva.utilitieslib.extensions.toHexaBytes

abstract class IsoPciOperation(operData: EOperation): IsoOperation(operData) {

    protected val hsm: Hsm = Hsm()

    //Implements Package
    override fun packField55() {}

    override fun packField56() {
        val value = "000A50583652363533353034010B42425641525432395F3130020E52544D563235323931313233323915133839333430373632303030303935333935313816013220023530"
        outputMsg.setField(56, value.toHexaBytes())
    }

    override fun packField60() {}

    override fun packField63() {
        val value = "455A373230303130313030303132453930313030303030353030303030303430303133383030303046E8176CB244A89111C1B53C2845E364ACAC7DDA4F9692C5393435363242423932324545523731334E202020202020202020202020435A34303031353820202020202020202020202020202020202020202020202020202020202020202020202051363036303030363033514B3438F99780D2802DBD3FD2173FDF310A4FAE7DB2BD7AA89F0B5B172D2861761725F442E61AFCF1A4964D4135453738443738515230325320"
        outputMsg.setField(63, value.toHexaBytes() )
    }

    override fun unpackField63() {}

    override fun packSpecificFields(): EReason = EReason.PACK_SPECIFIC_FIELD_SUCCESS
    override fun unpackSpecificFields(): EReason  = EReason.UNPACK_SPECIFIC_FIELD_SUCCESS

    //Implement Encrypt
    override fun encryptRequest(request: ByteArray ): ByteArray {
        return hsm.encrypt(Hsm.EStoreIndex.DATA.ordinal, IHsm.EEncryptMode.CBC, request)
    }

    //Implement Decrypt
    override fun decryptResponse(response: ByteArray ): ByteArray {
        return hsm.decrypt(Hsm.EStoreIndex.DATA.ordinal, IHsm.EEncryptMode.CBC, response)
    }
}