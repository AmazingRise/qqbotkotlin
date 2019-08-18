package parser

import com.squareup.moshi.Moshi
import data.Global
import network.Client
import parser.message.MessageParser
import parser.message.Request
import util.Prompt


class RawRequestParser {
    fun parse(rawRequest: String):String {
        //Parse raw request
        val request = Moshi.Builder().build().adapter<Request>(Request::class.java).fromJson(rawRequest)?:return ""

        //Sort message type
        when (request.post_type){
            "notice" -> {
                if (request.notice_type=="group_increase" && isCoolDownEnded()){
                    Client.sendMessageToGroup(request.group_id,"Welcome, freshman! ( ´∀｀)σ")
                    return "Welcome freshman!"
                }
            }
            "message" ->{
                val result = MessageParser().messageProcessor(request)
                if (result!="" && isCoolDownEnded()){
                    Prompt.echo("${request.message} acitvated cool down.")
                    return result
                } else {
                    return ""
                }
            }
            "request"->{
                //TODO: Add friend request responding
            }
        }

        return ""
    }

    private fun isCoolDownEnded(): Boolean{
        //Suspend function
        if (Global.suspendService) {
            return false
        }
        //Cool down limitation
        val now = System.currentTimeMillis()
        if ((now - Global.lastActivateTime) < Global.coolDownSeconds.toLong() * 1000) {
            return false
        } else {
            Global.lastActivateTime = System.currentTimeMillis()
        }
        return true
    }

    // TODO: Complete the symbol filter
    private fun symbolFilter(rawString: String): String {
        return rawString.replace(Regex("[-+.^:,]"), "")
    }


}