package parser

import data.Global
import util.Prompt

class KeywordMatcher {

    fun match(content: String, source: Long):String {
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