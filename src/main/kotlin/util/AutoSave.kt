package util

import data.Global
import data.PromptLogType
import java.io.File
import java.time.LocalDateTime
import java.util.*
import kotlin.concurrent.schedule

object AutoSave {
    fun autoLoad(location:String){
        //autoLoad() is called upon the startup of the main function.
        val file = File(location)
        if (file.exists()) {
            Archive.loadKeywordsFromFile(file)
            Prompt.echo("Archived keyword loaded.", PromptLogType.OK)
        }
    }
    fun autoSaveWatchDog(interval: Long){
        Timer().schedule(interval) {
            Archive.saveKeywordsToFile(File(Global.defaultArchiveLocation))
            Prompt.echo("Auto saved at ${LocalDateTime.now()}.")
        }
    }
}