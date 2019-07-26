package network

import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import data.LogType
import parser.RawRequestParser
import util.Prompt
import java.io.BufferedReader
import java.net.InetSocketAddress

object Server {
    private var server : HttpServer? = null
    private val httpHandler = HttpHandler { httpExchange ->
        //Get request body
        val requestBody = httpExchange.requestBody.bufferedReader().use(BufferedReader::readText)
        Prompt.echo(requestBody)

        //Get reply
        //val reply = getResponse(requestBody)

        //Generate response header
        val headers = httpExchange.responseHeaders!!
        headers.add("Content-Type", "application/json")
        //HTTP Status Code 200, response length 0
        //I was too lazy to calculate the response length, so I wrote 0 directly.
        //It doesn't matter. :P
        httpExchange.sendResponseHeaders(200, 0)

        val out = httpExchange.responseBody!!.writer()

        out.write("""{"reply":"${RawRequestParser().parse(requestBody)}", "at_sender" : false}""")
        out.close()
    }

    fun start(port: Int) {
        try {
            server = HttpServer.create(InetSocketAddress(port), 0)
            server?.createContext("/", httpHandler)
            server?.start()
            Prompt.echo("Serving HTTP on port $port ...",LogType.OK)
        } catch (e: Exception) {
            e.printStackTrace()
            Prompt.echo("Failed to start HTTP server.",LogType.FAILED)
        }
    }

    fun stop() {
        if (server == null) {
            Prompt.echo("No server is running.",LogType.FAILED)
            return
        }
        try {
            server?.stop(3)
            Prompt.echo("Server stopped.",LogType.OK)
        } catch (e: Exception) {
            e.printStackTrace()
            Prompt.echo("Failed to stop HTTP server.",LogType.FAILED)
        }
    }
}