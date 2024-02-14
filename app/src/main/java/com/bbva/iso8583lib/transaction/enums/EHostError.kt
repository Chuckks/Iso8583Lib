package com.bbva.iso8583lib.transaction.enums

import com.bbva.utilitieslib.extensions.toHexaAscii
import com.bbva.utilitieslib.extensions.toHexaString
import com.bbva.utilitieslib.security.KeyUtils
import java.security.InvalidParameterException

enum class EHostError( val value: String) {
    CODE_00( "APROBADA"),
    CODE_01("LLAMAR AL EMISOR"),
    CODE_02("LLAMAR AL EMISOR"),
    CODE_03("COMERCIO INVALIDO"),
    CODE_04("DECLINADA TARJETA RESTRINGIDA"),
    CODE_05("DECLINADA"),
    CODE_06("ERROR DE FORMATO"),
    CODE_07("DECLINADA TARJETA RESTRINGIDA"),
    CODE_08("DECLINADA"),
    CODE_09("DECLINADA"),
    CODE_10("APROBACION PARCIAL / NO SOPORTADA"),
    CODE_12("TRANSACCION INVALIDA"),
    CODE_13("MONTO INVALIDO"),
    CODE_14("TARJETA INVALIDA"),
    CODE_15("TARJETA INVALIDA"),
    CODE_16("TRANSACCION INVALIDA"),
    CODE_17("TARJETA CANCELADA"),
    CODE_18("DECLINADA"),
    CODE_19("TRANSACCION NO PERMITIDA"),
    CODE_20("TARJETA CANCELADA"),
    CODE_21("DECLINADA"),
    CODE_22("ERROR DE FORMATO"),
    CODE_23("TRANSACCION INVALIDA"),
    CODE_24("DECLINADA"),
    CODE_25("DECLINADA"),
    CODE_26("DECLINADA"),
    CODE_27("DECLINADA"),
    CODE_28("DECLINADA"),
    CODE_29("DECLINADA"),
    CODE_30("ERROR DE FORMATO"),
    CODE_31("CUENTA NO ENCONTRADA"),
    CODE_32("DECLINADA"),
    CODE_33("TARJETA EXPIRADA"),
    CODE_34("TRANSACCION DECLINADA"),
    CODE_35("DECLINADA TARJETA RESTRINGIDA"),
    CODE_36("DECLINADA TARJETA RESTRINGIDA"),
    CODE_37("DECLINADA TARJETA RESTRINGIDA"),
    CODE_38("NUMERO DE INTENTOS DE PIN EXCEDIDO"),
    CODE_39("NO EXISTE CUENTA"),
    CODE_40("FUNCION NO SOPORTADA"),
    CODE_41("TARJETA EXTRAVIADA"),
    CODE_42("NO EXISTE CUENTA"),
    CODE_43("DECLINADA TARJETA RESTRINGIDA"),
    CODE_44("NO EXISTE CUENTA"),
    CODE_45("PROMOCION NO PERMITIDA"),
    CODE_46("MONTO INFERIOR MIN PROMO"),
    CODE_47("LIMITE EXCEDIDO"),
    CODE_48("CVV/CVC2 REQUERIDO"),
    CODE_49("DECLINADA CVV2/CVC2 INVALIDO"),
    CODE_50("HA SUPERADO EL NUMERO DE TRANSACCIONES RECHAZADAS"),
    CODE_51("FONDOS INSUFICIENTES"),
    CODE_52("NO EXISTE CUENTA"),
    CODE_53("NO EXISTE CUENTA"),
    CODE_54("TARJETA VENCIDA"),
    CODE_55("PIN INCORRECTO"),
    CODE_56("NO EXISTE CUENTA"),
    CODE_57("TRANSACCION NO PERMITIDA"),
    CODE_58("TRANSACCION NO PERMITIDA"),
    CODE_59("TRANSACCION DECLINADA"),
    CODE_60("DECLINADA"),
    CODE_61("LIMITE DE MONTO EXCEDIDO"),
    CODE_62("TARJETA RESTRINGIDA"),
    CODE_63("TRANSACCION DECLINADA"),
    CODE_64("MONTO INVALIDO"),
    CODE_65("Declinada, inserte tarjeta"),
    CODE_66("TRANSACCION DECLINADA"),
    CODE_67("DECLINADA TARJETA RESTRINGIDA"),
    CODE_68("DECLINADA"),
    CODE_69("NUMERO DE CELULAR NO ASOCIADO CUENTA EXPRESS"),
    CODE_70("ERROR TRACK 2 DESCIFRADO"),
    CODE_71("INICIALIZAR LLAVES"),
    CODE_72("PROBLEMA INICIALIZACION LLAVES"),
    CODE_73("ERROR SISTEMA CRC"),
    CODE_74("ERROR CIFRADO DE DATOS PIN"),
    CODE_75("NUMERO DE INTENTOS DE PIN EXCEDIDOS"),
    CODE_76("CUENTA INEXISTENTE O BLOQUEADA"),
    CODE_77("ERROR CIFRADO DE DATOS"),
    CODE_78("LLAMAR AL EMISOR"),
    CODE_79("DECLINADA"),
    CODE_80("DECLINADA"),
    CODE_81("DECLINADA"),
    CODE_82("CVV/CVCV2 INCORRECTO TARJETA DIGITAL O TOKENIZADA"),
    CODE_83("TRANZACCION DECLINADA"),
    CODE_84("DECLINADA"),
    CODE_85("VALIDACION DE CUENTA MONTO CERO"),
    CODE_86("PIN INCORRECTO"),
    CODE_87("TRANSACCION INVALIDA"),
    CODE_88("ERROR DE CIFRADO DE DATOS"),
    CODE_89("PIN INCORRECTO"),
    CODE_90("DECLINADA"),
    CODE_91("SWITCH CAIDO"),
    CODE_92("SWITCH CAIDO"),
    CODE_93("TRANSACCION NO DISPONIBLE"),
    CODE_94("TRANSACCION DUPLICADA"),
    CODE_95("DECLINADA"),
    CODE_96("SWITCH CAIDO"),
    CODE_A1("Declinada, inserte tarjeta"),
    CODE_A3("LIMITE DE SALDO SUPERADO"),
    CODE_A4("EXCEDE EL NUMERO DE DEPOSITOS PERMITIDO"),
    CODE_B1("TRANSACCION CON DATOS DE CAMPAÑA"),
    CODE_B2("SERVICIO NO DISPONIBLE. PROMOCIONES ESPECIALES"),
    CODE_B3("TARJETA RESTRINGIDA"),
    CODE_B4("DECLINADA"),
    CODE_B5("DECLINADA"),
    CODE_C1("PRODUCTO NO DEFINIDO"),
    CODE_C2("PRODUCTO VENDIDO"),
    CODE_C3("PRODUCTO INVALIDO PARA VENTA"),
    CODE_C4("PROMOCION FINALIZADA"),
    CODE_C5("SIN AUTORIZACION DE VENTA"),
    CODE_C6("VENTA NO PERMITIDA DE PRODUCTO"),
    CODE_C7("VENTA NO PERMITIDA POR TIPO DE TRANSACCION"),
    CODE_C8("PLAZOS NO DEFINIDOS"),
    CODE_C9("NUMERO MAXIMO DE VENTA"),
    CODE_CA("MONTO DE TRANSACCION INVALIDO"),
    CODE_CB("PRODUCTO NO PUEDE SER DEVUELTO"),
    CODE_D1("CONVERSION DCC"),
    CODE_D2("ERROR EN EL CHECK-OUT - REINTENTE"),
    CODE_D3("ERROR EN EL MONTO DE CONVERSION DE DCC"),
    CODE_D5("FONDOS INSUFICIENTES RETIRO SIN TARJETA"),
    CODE_D6("CLAVE INVALIDA RETIRO SIN TARJETA"),
    CODE_D7("RETIRO COBRADO OTP COBRADO"),
    CODE_D8("MONTO INVALIDO"),
    CODE_E1("ERROR DE CRIPTOGRAMA PIN EN LINEA"),
    CODE_E2("VALIDAR APP TARJETAHABIENTE"),
    CODE_N0("SWITCH CAIDO"),
    CODE_N1("SWITCH CAIDO"),
    CODE_N2("SWITCH CAIDO"),
    CODE_N3("TRANSACCION NO PERMITIDA"),
    CODE_N4("LIMITE DE MONTO EXCEDIDO"),
    CODE_N7("DECLINADA CVV2/CVC2 INVALIDO"),
    CODE_N8("LIMITE DE MONTO EXCEDIDO"),
    CODE_P5("PIN INCORRECTO"),
    CODE_P6("PIN INCORRECTO"),
    CODE_Q1("PIN INCORRECTO"),
    CODE_R0("DECLINADA"),
    CODE_R1("TRANSACCION DECLINADA"),
    CODE_R2("PIN INCORRECTO"),
    CODE_R3("TRANSACCION DECLINADA"),
    CODE_R9("DECLINADA"),
    CODE_S0("DECLINADA"),
    CODE_U0("DECLINADA"),
    CODE_U1("DECLINADA"),
    CODE_U2("DECLINADA"),
    CODE_U3("DECLINADA"),
    CODE_U4("DECLINADA"),
    CODE_U5("DECLINADA"),
    CODE_U6("DECLINADA"),
    CODE_U7("DECLINADA"),
    CODE_U8("DECLINADA"),
    CODE_V0("DECLINADA"),
    CODE_V1("DECLINADA"),
    CODE_V2("DECLINADA"),
    CODE_V3("DECLINADA"),
    CODE_V4("DECLINADA"),
    CODE_V7("DECLINADA"),
    CODE_V8("DECLINADA"),
    CODE_V9("DECLINADA"),
    CODE_Y1("DECLINADA"),
    CODE_Y3("DECLINADA"),
    CODE_Z1("DECLINADA"),
    CODE_Z3("DECLINADA");

    companion object{

        fun translateError(value: ByteArray): EHostError{
            val code = "CODE_${toHexaAscii(value)}"
            return values().find { it.name == code } ?: throw InvalidParameterException("Code [$code] is not supported")
        }

        fun toHexaAscii(value: ByteArray) = value.toHexaString().toHexaAscii()

        fun findValue(code: String ) = values().find { it.name == "CODE_$code" }?.value ?: InvalidParameterException("Code [$code] is not supported")
    }
}