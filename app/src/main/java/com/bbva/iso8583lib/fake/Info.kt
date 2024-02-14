package com.bbva.iso8583lib.fake

import com.bbva.devicelib.physical.IInfo

class Info: IInfo {

    private fun translateType(type: IInfo.EType): String {
        return when (type) {
            IInfo.EType.SDK_VERSION           -> "0.0"
            IInfo.EType.HARDWARE_VERSION      -> "0.0"
            IInfo.EType.FIRMWARE_VERSION      -> "0.0"
            IInfo.EType.SERIAL_NUMBER         -> "1234567890"
            IInfo.EType.DEVICE_CODE           -> "0.0"
            IInfo.EType.DEVICE_MODEL          -> "0.0"
            IInfo.EType.EMV_VERSION           -> "0.0"
            IInfo.EType.EMV_KERNEL_CHECKSUM   -> "0000"
            IInfo.EType.IS_CHARGING           -> "0.0"
            IInfo.EType.IS_KEYBOARD_AVAILABLE -> "0.0"
            IInfo.EType.IS_PRINTER_AVAILABLE  -> "0.0"
            IInfo.EType.BATTERY_PERCENTAGE    -> "0.0"
            IInfo.EType.MANUFACTURE           -> "0.0"
            IInfo.EType.MAC_ADDRESS           -> "0.0.0.0"
            IInfo.EType.POS_ID                -> "00000"
        }
    }

    override fun getInfo(type: IInfo.EType) = translateType(type)

}