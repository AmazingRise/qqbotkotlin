package main

import data.Global
import util.Archive
import util.Prompt
import java.io.File

fun main(args: Array<String>) {
    val file = File(Global.defaultArchiveLocation)
    if (file.exists()) Archive.loadKeywordsFromFile(file)
    network.Server.start(8080)
    Prompt.run()
}