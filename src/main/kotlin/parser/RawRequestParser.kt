package parser

import com.squareup.moshi.Moshi
import data.ErrorLevel
import data.Global
import data.Request
import network.Client
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
                val result = messageProcessor(request)
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

    private fun messageProcessor(request: Request): String {

        //Process operator commands
        if (request.user_id in Global.operators) {
            val superCommandResult = CommandInterpreter().parseSuperCommand(request.message)
            if (superCommandResult.errorLevel != ErrorLevel.NOTFOUND) {
                return superCommandResult.content
            }
        }

        //Blacklist function
        if (request.user_id in Global.blacklist) {
            return ""
        }

        //Parse group command
        //TODO: Add filter to ensure safety.
        val groupCommandResult = CommandInterpreter().parseGroupCommand(request.message, request.group_id)
        if (groupCommandResult.errorLevel != ErrorLevel.NOTFOUND) {
            return groupCommandResult.content
        }

        //Match the keywords
        val keywordResult = matchKeyword(request.message, request.group_id)
        if (keywordResult !="") {
            return keywordResult
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

    private fun matchKeyword(content: String, source: Long):String {
        //Prompt.echo(content)
        for (keyword in Global.replyDictionary[source]!!.keys)
        {
            if (content.contains(keyword)) {
                Prompt.echo("Match:$source")
                return Global.replyDictionary[source]!![keyword]!!
            }
        }
        return ""
    }
}