package util

import data.Global
import data.PromptLogType
import parser.command.CommandInterpreter
import parser.command.ErrorLevel

object Prompt {
    private const val promptSymbol = "root#"
    fun echo(text: String) {
        if (Global.printInfo) {
            echo(text, PromptLogType.INFO)
        }
    }
    fun echo(text: String, promptLogType: PromptLogType) {
        when (promptLogType) {
            PromptLogType.OK      -> print("[  OK  ] ")
            PromptLogType.FAILED  -> print("[FAILED] ")
            PromptLogType.INFO    -> print("[ INFO ] ")
        }
        println(text)
    }
    fun run(){
        while (true){
            print(promptSymbol)
            val input: String = readLine() ?: continue
            //TODO: Some prompt only command, like group chatting env. simulation
            val result = CommandInterpreter().parseSuperCommand(input)
            when (result.errorLevel){
                ErrorLevel.NOTFOUND -> {
                    echo("Command not found.", PromptLogType.FAILED)
                }
                ErrorLevel.SUCCESS -> {
                    echo(result.content, PromptLogType.OK)
                }
                ErrorLevel.FAILED -> {
                    echo(result.content, PromptLogType.FAILED)
                }
            }
        }
    }
}