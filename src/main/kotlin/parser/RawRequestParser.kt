package parser

import com.squareup.moshi.Moshi
import data.Global
import data.Request

class RawRequestParser {
    fun parse(rawRequest: String):String {
        //Parse raw request
        val request = Moshi.Builder().build().adapter<Request>(Request::class.java).fromJson(rawRequest)?:return ""

        //Sort message type
        when (request.post_type){
            "notice" -> {
                if (request.notice_type=="group_increase"){
                    return "Welcome freshman!"
                }
            }
            "message" ->{
                return messageProcessor(request)
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
            if (superCommandResult != "") {
                return superCommandResult
            }
        }

        //Blacklist function
        if (request.user_id in Global.blacklist) {
            return ""
        }

        //Suspend function
        if (Global.suspendService) {
            return ""
        }

        //Cool down limitation
        val now = System.currentTimeMillis()
        if ((now - Global.lastActivateTime) < Global.coolDownSeconds.toLong() * 1000) {
            return ""
        } else {
            Global.lastActivateTime = System.currentTimeMillis()
        }

        //Parse group command
        //TODO: Add filter to ensure safety.
        //TODO: Solve the problem of specific symbols.
        val groupCommandResult = CommandInterpreter().parseGroupCommand(request.message, request.group_id)
        if (groupCommandResult != "") {
            return groupCommandResult
        }

        //Match the keywords
        val keywordResult = KeywordMatcher().match(request.message, request.group_id)
        if (keywordResult !="") {
            return keywordResult
        }
        //TODO: "Remind me" implementation.

        return ""
    }
}