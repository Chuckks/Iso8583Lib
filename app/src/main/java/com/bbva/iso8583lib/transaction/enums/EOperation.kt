package com.bbva.iso8583lib.transaction.enums


enum class EOperation( val messageType: Int, val processingCode: Int, val txStored: Boolean) {


    SALE(200,0, true),
    POINT_SALE(200, 180000, true),
    POINT_QUERY(200, 160000, false),

    //None
    NONE(0,0, false)

}