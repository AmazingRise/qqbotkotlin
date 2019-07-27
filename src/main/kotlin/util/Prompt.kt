package util

import data.Global
import data.LogType
import parser.CommandInterpreter

object Prompt {
    val promptSymbol = "Robot>"

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
            echo("Robot prompt is ready.", LogType.OK)
            print(promptSymbol)
            val input: String = readLine() ?: continue
            val result = CommandInterpreter().parseSuperCommand(input)
            if (result != "") {
                echo(result, LogType.OK)
            }
        }
    }
}