package parser

data class ParserResponse (
    var reply:String,
    val fromGroupId:Long,
    val fromUserId:Long
)