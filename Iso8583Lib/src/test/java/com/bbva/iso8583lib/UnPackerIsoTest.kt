package com.bbva.iso8583lib

import com.bbva.iso8583lib.iso.UnpackerIso
import com.bbva.iso8583lib.iso.data.EFormat
import com.bbva.iso8583lib.iso.data.Field
import com.bbva.iso8583lib.iso.data.Version
import org.junit.Test
import org.junit.Assert.*

class UnpackerIsoTest {

    private var unpackerIso: UnpackerIso =
        UnpackerIso(initVersion = IsoFileTest.DEFAULT_VERSION)
    @Test(expected = ExceptionInInitializerError::class)
    fun validateGetItemTest() {
        unpackerIso.getItem(2)
    }

    @Test
    fun checkVersionEqualsTest() {
        assertFalse(unpackerIso.unpack)
        unpackerIso.unPacker(IsoFileTest.file)

        assertTrue(unpackerIso.unpack)
        assertEquals(IsoFileTest.DEFAULT_VERSION.compareTo(unpackerIso.initVersion), 0)
    }

    @Test
    fun searchExistingFieldTest() {
        assertFalse(unpackerIso.unpack)
        unpackerIso.unPacker(IsoFileTest.file)

        val fieldToSearch = Field( 4, "Amount, transaction", EFormat.FIX_NUMERIC.value, 12)
        val result = unpackerIso.search(fieldToSearch)

        assertEquals(fieldToSearch, result)
    }

    @Test
    fun searchNotExistingFieldTest() {
        assertFalse(unpackerIso.unpack)
        unpackerIso.unPacker(IsoFileTest.file)

        val fieldToSearch = Field( 99, "NonExistingField", EFormat.FIX_NUMERIC.value, 4)
        val result = unpackerIso.search(fieldToSearch)

        assertNotEquals(fieldToSearch, result)
    }
}
