package com.bbva.iso8583lib.transaction.interfaces

interface IPackage {
    //Implement Pack
    fun packDefault( )
    fun packField56( )
    fun packField63( )
    fun packSpecificFields( )

    //Implement Unpacks
    fun unpackDefault( )

    fun unpackField60( ) : Boolean
    fun unpackSpecificFields( ): Boolean
}