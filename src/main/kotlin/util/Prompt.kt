package util

import data.ErrorLevel
import data.Global
import data.LogType
import parser.CommandInterpreter

object Prompt {
    private const val promptSymbol = "root#"
    fun echo(text: String) {
        if (Global.printInfo) {
            echo(text, LogType.INFO)
        }
    }
    fun echo(text: String, logType: LogType) {
        when (logType) {
            LogType.OK      -> print("[  OK  ] ")
            LogType.FAILED  -> print("[FAILED] ")
            LogType.INFO    -> print("[ INFO ] ")
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
                    echo("Command not found.", LogType.FAILED)
                }
                ErrorLevel.SUCCESS -> {
                    echo(result.content, LogType.OK)
                }
                ErrorLevel.FAILED -> {
                    echo(result.content, LogType.FAILED)
                }
            }
        }
    }
}