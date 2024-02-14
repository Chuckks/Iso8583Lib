package com.bbva.iso8583lib

import com.bbva.iso8583lib.interfaces.IOperation
import com.bbva.iso8583lib.transaction.operations.Sale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun SaleTest() {
        CoroutineScope(Dispatchers.IO).launch {
            Sale().execute(IOperation.InputData())
        }

        assertEquals(4, 2 + 2)
    }
}