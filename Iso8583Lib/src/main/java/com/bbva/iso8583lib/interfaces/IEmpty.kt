package com.bbva.iso8583lib.interfaces

interface IEmpty {
    fun isEmpty():Boolean
    fun isNotEmpty() = !isEmpty()
}