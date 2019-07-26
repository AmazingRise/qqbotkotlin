package parser

import com.squareup.moshi.Moshi
import data.Message

class RawRequestParser {
    fun parse(rawRequest: String):String {
        val requestStructure = Moshi.Builder().build().adapter(Message::class.java).fromJson(rawRequest)
        var result = CommandInterpreter().parseGroupCommand(requestStructure!!.message,requestStructure!!.group_id)
        if (result == "") {
            return KeywordMatcher().match(requestStructure!!.message,requestStructure!!.group_id)
        }else{
            return result
        }
    }
}