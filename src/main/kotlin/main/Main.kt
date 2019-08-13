package main

import data.Global
import util.AutoSave
import util.Prompt
import kotlin.concurrent.thread


fun main(args: Array<String>) {
    AutoSave.autoLoad(Global.defaultArchiveLocation)
    network.Server.start(Global.localPort)
    thread{
        Prompt.run()
    }
    thread{
        AutoSave.autoSaveWatchDog(Global.autoSaveDuration)
    }
}