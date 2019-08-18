package data

class CommandResult(resposeContent:String, responseErrorLevel: ErrorLevel) {
    val content: String = resposeContent
    val errorLevel: ErrorLevel = responseErrorLevel
}