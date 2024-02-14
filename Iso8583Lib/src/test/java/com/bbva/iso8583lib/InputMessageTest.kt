package com.bbva.iso8583lib

import com.bbva.iso8583lib.iso.InputMessage
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
        val field22 = inputMessage.getField(22)
        val field23 = inputMessage.getField(23)
        val field37 = inputMessage.getField(37)
        val field41 = inputMessage.getField(41)
        val field48 = inputMessage.getField(48)
        val field49 = inputMessage.getField(49)
        val field55 = inputMessage.getField(55)
        val field56 = inputMessage.getField(56)
        val field60 = inputMessage.getField(60)
        val field63 = inputMessage.getField(63)
    }
}