package util

import data.Global
import data.LogType
import parser.CommandInterpreter

object Prompt {
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
            val input: String = readLine() ?: continue
            CommandInterpreter().parseSuperCommand(input)
        }
    }
}