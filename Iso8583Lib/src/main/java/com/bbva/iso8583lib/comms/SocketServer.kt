package com.bbva.iso8583lib.comms

import android.util.Log
import com.bbva.iso8583lib.utils.Constant
import com.bbva.utilitieslib.extensions.toHexaBytes
import com.bbva.utilitieslib.interfaces.IEmpty
import com.bbva.utilitieslib.utils.TimeSpan
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

private const val DEFAULT_OPENED = false
private const val DEFAULT_FRAME_SIZE: Int = 4096
private val DEFAULT_CONNECT_TIMEOUT = TimeSpan(15)
private val DEFAULT_RECEIVE_TIMEOUT = TimeSpan( 60 )

private val TAG = Constant.BBVA_PREFIX + SocketServer::class.java.simpleName

class SocketServer(val server: ServerData = ServerData(),
                   val maxFrameLength: Int = DEFAULT_FRAME_SIZE) : ISocket, IEmpty {

    private var socket: Socket = Socket()
    private var received: ByteArray = ByteArray(maxFrameLength)
    private var opened: Boolean = DEFAULT_OPENED

    private var connectTimeout: TimeSpan = DEFAULT_CONNECT_TIMEOUT
    private var readTimeout: TimeSpan  = DEFAULT_RECEIVE_TIMEOUT

    private var outputStream: OutputStream = ByteArrayOutputStream()
    private var inputStream: InputStream = ByteArrayInputStream(byteArrayOf())

    override fun isEmpty() = (server.isEmpty() && maxFrameLength == DEFAULT_FRAME_SIZE)

    //TODO decomentar codigo, temporal para pruebas
    override fun connect(): Boolean {
        return true
      /*  if (!isOpened()) {
            if (server.isEmpty())
                throw ExceptionInInitializerError("Not configure Server Before")

            socket.let { sk ->
                sk.connect(InetSocketAddress(server.ip, server.port), connectTimeout.seconds)
                sk.soTimeout = readTimeout.milliSeconds

                inputStream = sk.getInputStream()
                outputStream = sk.getOutputStream()
                Log.i(TAG, "Connection established and configured")
                opened = true
            }
        }else
            Log.i(TAG, "Connection already is open")

        return opened
       */
    }

    override fun disconnect(): Boolean {
        if (!isOpened()){
            Log.i(TAG, "Connection already is closed")
            return true
        }

        socket.let {
            it.close()
            opened = false
        }

        return true
    }

    override fun isOpened() = opened
    override fun send(data: String): Boolean = send(data.toByteArray())

    override fun send(data: ByteArray): Boolean{
        if (data.isEmpty())
            return false

        return true

        /*return if (isOpened()) {
            Log.i(TAG, "OUTPUT_DATA [${data.toHexaString()}] SIZE[${data.contentToString().length}")
            outputStream.write(data)
            true
        } else false

         */
    }

    private val value = "0100600000002302102020000006800342000000000005303451303046303030303030303030310012910A7209E74C89C3D17F30300058060C32343032303631363231333907023030100842414E434F4D4552110242431206437265646974130C5441524A45544120415A554C2102303000700000000000000000000000010000004000000000040000000000400000FFFFFF000000000000000000000000000000000000000000002010000000000015200000400004840F0069523731334E202020202020202020202020514A343841CF1AFF0106F3F3CCE0FE57219D1A30C19CFDF3DFFD2547635FC3A8473D34D02BE90D0E4B3B2EA84334373933453237"
    override fun receive() = value.toHexaBytes()

    /*    if (isOpened()){
            val lengthToReceive = inputStream.read(received)

            if( lengthToReceive > maxFrameLength )
                throw IndexOutOfBoundsException("len received [$lengthToReceive] >  MaxLengthSize [$maxFrameLength]")

            Log.i(TAG, "INPUT_DATA [${received.toHexaString()}] SIZE [${received.contentToString().length}]]")
            received
        }else{
            Log.i(TAG, "Can't Read content Open[${isOpened()}]")
            ByteArray(0)
        }

     */
}
