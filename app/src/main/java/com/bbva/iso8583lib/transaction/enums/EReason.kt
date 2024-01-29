package com.bbva.iso8583lib.transaction.enums

enum class EReason {
    NONE,
    NOT_INIT_MODULE,
    LOAD_KEYS,
    CRYPTO_REQUEST_SUCCESS,
    CRYPTO_RESPONSE_SUCCESS,
    SERVICE_CONNECTION,
    SERVICE_DISCONNECT,
    IO_EXCEPTION,
    SEND_TX_SUCCESS,
    RECEIVE_TX_SUCCESS,
    PREPARE_SUCCESS,
    INIT_SUCCESS,
    PRECONDITION_SUCCESS,
    RESPONSE_SUCCESS,
    OPERATION_FAIL,
    OPERATION_SUCCESS,
    SERVER_CONNECT_SUCCESS,
    SERVER_DISCONNECT_SUCCESS
}