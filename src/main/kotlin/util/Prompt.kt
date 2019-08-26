package util

import data.Global
import data.PromptLogType
import parser.command.CommandInterpreter
import parser.command.ErrorLevel

object Prompt {
    private const val promptSymbol = "root#"
    fun echo(text: String):String {
        return echo(text, PromptLogType.INFO)
    }
    fun echo(text: String, promptLogType: PromptLogType):String {
        var result = ""
        when (promptLogType) {
            PromptLogType.OK -> {
                result = "[  OK  ] $text\n"
                print(result)
            }
            PromptLogType.FAILED -> {
                result = "[FAILED] $text\n"
                print(result)
            }
            PromptLogType.INFO -> {
                result = "[ INFO ] $text\n"
                if (Global.printInfo) {
                    print(result)
                }
            }
        }
        return result
    }
    fun run(){
        while (true){
            print(promptSymbol)
            val input: String = readLine() ?: continue
            exec(input)
        }
    }

    fun exec(command:String):String{
        //TODO: Some prompt only command, like group chatting env. simulation
        val commandResult = CommandInterpreter().parseSuperCommand(command)
        return when (commandResult.errorLevel){
            ErrorLevel.NOTFOUND -> {
                echo("Command not found.", PromptLogType.FAILED)
            }
            ErrorLevel.SUCCESS -> {
                echo(commandResult.content, PromptLogType.OK)
            }
            ErrorLevel.FAILED -> {
                echo(commandResult.content, PromptLogType.FAILED)
            }
        }
    }
}