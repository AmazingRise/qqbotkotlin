package parser

import data.Global

class KeywordMatcher {

    fun match(content: String, source: Long):String {
        //Prompt.echo(content)
        for (keyword in Global.replyDictionary[source]!!.keys)
        {
            if (content.contains(keyword)) {
                return Global.replyDictionary[source]!![keyword]!!
            }
        }
        return ""
    }
}