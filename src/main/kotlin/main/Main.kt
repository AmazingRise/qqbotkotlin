package main

import data.Global
import data.LogType
import util.Archive
import util.Prompt
import java.io.File


fun main(args: Array<String>) {
    val file = File(Global.defaultArchiveLocation)
    if (file.exists()) {
        Archive.loadKeywordsFromFile(file)
        Prompt.echo("Archived keyword loaded.",LogType.OK)
    }
    network.Server.start(8080)
    Prompt.run()
}