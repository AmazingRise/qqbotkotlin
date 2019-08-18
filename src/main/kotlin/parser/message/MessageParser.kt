package parser.message

import data.Global
import parser.command.CommandInterpreter
import parser.command.ErrorLevel
import util.Prompt

class MessageParser {

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

    fun messageProcessor(request: Request): String {

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


}