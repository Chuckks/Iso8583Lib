package com.bbva.iso8583lib

import com.bbva.iso8583lib.IsoFileTest.Companion.DEFAULT_ISO_FIELD_55
import com.bbva.iso8583lib.iso.OutputMessage
import com.bbva.iso8583lib.iso.UnpackerIso
import com.bbva.utilitieslib.extensions.toHexaBytes
import org.junit.Assert
import org.junit.Test

class OutputMessageTest {

    @Test
    fun fieldsConfiguredCorrectly(){
        val unpacker = UnpackerIso(IsoFileTest.DEFAULT_VERSION)
        unpacker.unPacker(IsoFileTest.file)
        Assert.assertTrue(unpacker.unpack)

        val outputMessage = OutputMessage(unpacker)
        outputMessage.header = "6000220000".toHexaBytes()
        outputMessage.setField(0, 200)
        outputMessage.setField(3, 0)
        outputMessage.setField(4, 90000)
        outputMessage.setField(11, 999999)
        outputMessage.setField(48, "004451761")
        outputMessage.setField(55, DEFAULT_ISO_FIELD_55.toHexaBytes())

        val packedData = outputMessage.pack()
        Assert.assertFalse(packedData.isEmpty())
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun fieldNotConfigured(){
        val unpacker = UnpackerIso(IsoFileTest.DEFAULT_VERSION)
        unpacker.unPacker(IsoFileTest.file)
        Assert.assertTrue(unpacker.unpack)

        val outputMessage = OutputMessage(unpacker)
        outputMessage.setField(0, 200)
        outputMessage.setField(99, "Hola Mundo")

        outputMessage.pack()
    }
}