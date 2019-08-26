package network

import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import data.Global
import data.PromptLogType
import parser.RawRequestParser
import util.Prompt
import java.io.BufferedReader
import java.net.InetSocketAddress
import java.net.URLEncoder
import kotlin.system.exitProcess

object Server {
    private var server : HttpServer? = null
    private val serverRequestHandler = HttpHandler { httpExchange ->
        //Get request body
        val requestBody = httpExchange.requestBody.bufferedReader().use(BufferedReader::readText)
        Prompt.echo(requestBody)
        when (httpExchange.requestMethod) {
            "POST" -> {
                //Generate response header
                val headers = httpExchange.responseHeaders!!
                headers.add("Content-Type", "application/x-www-form-urlencoded")
                //HTTP Status Code 200, response length 0
                //I was too lazy to calculate the response length, so I wrote 0 directly.
                //It doesn't matter. :P
                httpExchange.sendResponseHeaders(200, 0)

                val out = httpExchange.responseBody!!.writer()

                val parserResponse = RawRequestParser().parse(requestBody)
                sendMessageTo(parserResponse.reply,parserResponse.fromGroupId,parserResponse.fromUserId)
                out.write("{\"reply\":\"\"\", \"at_sender\" : false}")
                out.close()
            }
            "GET" -> {
                //val param = httpExchange.requestURI.query
                val headers = httpExchange.responseHeaders!!
                headers.add("Content-Type", "text/html")
                httpExchange.sendResponseHeaders(200, 0)
                val out = httpExchange.responseBody!!.writer()
                out.write(WebConsole.content)
                out.close()
            }
        }
    }

    private val logHandler = HttpHandler { httpExchange ->
        //val requestBody = httpExchange.requestBody.bufferedReader().use(BufferedReader::readText)
        val param = httpExchange.requestURI.query
        val headers = httpExchange.responseHeaders!!
        headers.add("Content-Type", "text/html")

        if (param!=null) WebConsole.logText += Prompt.exec(param)
        val result = WebConsole.logText
        httpExchange.sendResponseHeaders(200, result.length.toLong())
        val out = httpExchange.responseBody!!.writer()
        out.write(result)
        out.close()
    }


    fun start(port: Int) {
        try {
            server = HttpServer.create(InetSocketAddress(port), 0)
            server?.createContext("/", serverRequestHandler)
            server?.createContext("/exec", logHandler)
            server?.start()
            Prompt.echo("Serving HTTP on port $port ...",PromptLogType.OK)
        } catch (e: Exception) {
            e.printStackTrace()
            Prompt.echo("Failed to start HTTP server.",PromptLogType.FAILED)
            exitProcess(1)
        }
    }

    fun stop() {
        if (server == null) {
            Prompt.echo("No server is running.",PromptLogType.FAILED)
            return
        }
        try {
            server?.stop(3)
            Prompt.echo("Server stopped.",PromptLogType.OK)
        } catch (e: Exception) {
            e.printStackTrace()
            Prompt.echo("Failed to stop HTTP server.",PromptLogType.FAILED)
        }
    }

    private fun sendMessageTo(content: String,groupId:Long,friendId: Long){
        if (content=="") return
        Prompt.echo("Reply to $friendId @ $groupId: $content")
        val reply = URLEncoder.encode(content.replace("=",":"), "UTF-8")
        var parameters = "message=$reply"
        if (groupId==0L){
            parameters += "&user_id=$friendId"
        }else{
            parameters += "&group_id=$groupId"
        }
        khttp.get("http://${Global.remoteAddress}/send_msg?$parameters")
    }
}