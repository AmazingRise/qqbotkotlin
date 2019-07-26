package parser

import com.squareup.moshi.Moshi
import data.Global
import data.Message

class RawRequestParser {
    fun parse(rawRequest: String):String {
        //Parse raw request
        val requestStructure = Moshi.Builder().build().adapter(Message::class.java).fromJson(rawRequest)?:return ""

        if (requestStructure.user_id in Global.operators){
            val result = CommandInterpreter().parseSuperCommand(requestStructure.message)
            if (result!=""){
                return result
            }
        }
        val result = CommandInterpreter().parseGroupCommand(requestStructure.message,requestStructure.group_id)

        return if (result == "") {
            KeywordMatcher().match(requestStructure.message,requestStructure.group_id)
        }else{
            result
        }
    }
}