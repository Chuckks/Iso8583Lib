package com.bbva.iso8583lib.demo

import android.util.Log
import com.bbva.devicelib.physical.IHsm
import com.bbva.iso8583lib.fake.Hsm
import com.bbva.utilitieslib.security.KeyUtils

class HsmDemo {

    operator fun invoke() {
        /*
        kek: 111111111111111122222222222222223333333333333333
        tmk clean: 333333333333333322222222222222221111111111111111
        tmk cipher: 04FE9168BB0E7E7B1259B7E1FEC34B9D3BA9E4262634E755
        pik cipher: F54BC08718D47BF7B4C175181EC3942F1DBB5696FB24730B
        pik clean: 444444444444444455555555555555556666666666666666
         */

        val kek = KeyUtils.createSecretKey(KeyUtils.EAlgo.TDES, "111111111111111122222222222222223333333333333333")
        val tmk = KeyUtils.createSecretKey(KeyUtils.EAlgo.TDES, "04FE9168BB0E7E7B1259B7E1FEC34B9D3BA9E4262634E755")
        val pik = KeyUtils.createSecretKey(KeyUtils.EAlgo.TDES, "F54BC08718D47BF7B4C175181EC3942F1DBB5696FB24730B")

        val hsm = Hsm()
        var result = hsm.injectClearKey(IHsm.EKeyType.DUKPT_KEK, kek, Hsm.EStoreIndex.DUKPT_KEK.ordinal)
        Log.e("PinV2", "save KEK result:$result")

        result = hsm.injectEncryptedKey(IHsm.EKeyType.TMK, 0, tmk, Hsm.EStoreIndex.TMK.ordinal)
        Log.e("PinV2", "save TMK result:$result")

        result = hsm.injectEncryptedKey(IHsm.EKeyType.PIN, 1, pik, Hsm.EStoreIndex.PIN.ordinal)
        Log.e("PinV2", "save PIK result:$result")
    }
}
