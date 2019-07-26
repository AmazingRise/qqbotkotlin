package util

import data.Global
import data.LogType
import parser.CommandInterpreter

object Prompt {
    public fun echo(text: String) {
        if (Global.printInfo) {
            echo(text, LogType.INFO)
        }
    }
    public fun echo(text: String, logType: LogType) {
        when (logType) {
            LogType.OK      -> print("[  OK  ] ")
            LogType.FAILED  -> print("[FAILED] ")
            LogType.INFO    -> print("[ INFO ] ")
        }
        println(text)
    }
    public fun run(){
        while (true){
            var input = readLine()
            CommandInterpreter().parseSuperCommand(input)
        }
    }
}