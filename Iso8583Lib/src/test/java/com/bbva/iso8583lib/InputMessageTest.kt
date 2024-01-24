package com.bbva.iso8583lib

import com.bbva.iso8583lib.iso.InputMessage
import com.bbva.iso8583lib.iso.OutputMessage
import com.bbva.iso8583lib.iso.UnpackerIso
import com.bbva.utilitieslib.extensions.toHexaBytes
import org.junit.Assert
import org.junit.Test

class InputMessageTest {
    @Test
    fun fieldsConfiguredCorrectly(){
        val unpacker = UnpackerIso(IsoFileTest.DEFAULT_VERSION)
        unpacker.unPacker(IsoFileTest.file)
        Assert.assertTrue(unpacker.unpack)

        val inputMessage = InputMessage(unpacker)
        inputMessage.unpack(IsoFileTest.DEFAULT_BYTEARRAY_ISO.toHexaBytes())
        val field3 = inputMessage.getField(3)
        val field4 = inputMessage.getField(4)
        val field11 = inputMessage.getField(11)
        val field12 = inputMessage.getField(12)
        val field13 = inputMessage.getField(13)
    }
}