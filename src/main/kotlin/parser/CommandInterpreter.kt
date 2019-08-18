package parser

import data.CommandResult
import data.ErrorLevel
import data.Global
import data.LogType
import util.Archive
import util.Prompt
import java.io.File
import kotlin.system.exitProcess

class CommandInterpreter {
    /**
     * parseGroupCommand
     * (Command from conversations)
     * return: command result
     */
    fun parseGroupCommand(content: String, groupId: Long): CommandResult {
        val args = content.split(" ")
        val command = args[0]
        when (command) {
            "addkw" -> {
                try {
                    if (args.lastIndex < 2) return CommandResult("Missing the content of reply.",ErrorLevel.FAILED)
                    val keyword = args[1]
                    val reply = content.substringAfter(" ").substringAfter(" ")
                    if (Global.replyDictionary[groupId]!=null) {
                        Global.replyDictionary[groupId]!![keyword] = reply
                    }else{
                        val initHashMap = HashMap<String, String>()
                        initHashMap[keyword] = reply
                        Global.replyDictionary[groupId] = initHashMap
                    }
                    return CommandResult("Done. ($keyword,$reply)",ErrorLevel.SUCCESS)
                }  catch (e: Exception) {
                    e.printStackTrace()
                    return CommandResult("Failed to add keyword.",ErrorLevel.FAILED)
                }
            }
            "delkw" -> {
                try {
                    val param = content.split(" ")
                    val keyword = param[1]
                    if (Global.replyDictionary[groupId]!!.contains(keyword)) {
                        Global.replyDictionary[groupId]!!.remove(keyword)
                        return CommandResult("Removed. ($keyword)",ErrorLevel.SUCCESS)
                    }else{
                        return CommandResult("\"${keyword}\" does not exist.",ErrorLevel.FAILED)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    return CommandResult("Failed to remove keyword.",ErrorLevel.FAILED)
                }
            }
            "lstkw" -> {
                return CommandResult(Global.replyDictionary[groupId].toString(),ErrorLevel.SUCCESS)
            }
            "remind" -> {
                try {
                    val sentence = content.drop(6)
                    if (sentence=="")
                    {
                        Global.reminders.remove(args[1].toLong())
                        return CommandResult("Reminder of ${args[1].toLong()} has been removed.",ErrorLevel.FAILED)
                    }else{
                        Global.reminders.put(args[1].toLong(),sentence)
                        return CommandResult("Done. (Reminder of ${args[1].toLong()})",ErrorLevel.FAILED)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    return CommandResult("Add reminder failed.",ErrorLevel.FAILED)
                }
            }
            "syn" -> return CommandResult("ack",ErrorLevel.SUCCESS)
            //TODO: Complete the status query.
            "status" ->{
                return CommandResult("",ErrorLevel.NOTFOUND)
            }
        }
        return CommandResult("",ErrorLevel.NOTFOUND)
    }

    /**
     * parseSuperCommand
     * (Command from conversations and command)
     */
    fun parseSuperCommand(superCommand: String):CommandResult {
        //TODO: Return flexibly
        val args = superCommand.split(" ")
        try {
            when (args[0]) {
                "reset" -> {
                    Global.replyDictionary.clear()
                    return CommandResult("Reset done.",ErrorLevel.SUCCESS)
                }
                "load" -> {
                    return if (Archive.loadKeywordsFromFile(File(args[1]))) {
                        CommandResult("Keyword has been loaded.",ErrorLevel.SUCCESS)
                    } else CommandResult("Error loading configuration file.",ErrorLevel.SUCCESS)
                }
                "save" -> {
                    return if (Archive.saveKeywordsToFile(File(args[1]))) {
                        CommandResult("Keyword has been saved to ${args[1]}.",ErrorLevel.SUCCESS)
                    } else CommandResult("Error saving configuration file.",ErrorLevel.SUCCESS)
                }
                "stop_server" -> {
                    Prompt.echo("Shutdown signal received.",LogType.OK)
                    if (!Archive.saveKeywordsToFile(File(Global.defaultArchiveLocation))) {
                        Prompt.echo("Error saving configuration file.", LogType.FAILED)
                    }
                    network.Server.stop()
                    exitProcess(0)
                }
                "toggle" -> {
                    return CommandResult(toggle(args[1]),ErrorLevel.SUCCESS)
                }
                "addop" -> {
                    try {
                        Global.operators.add(args[1].toLong())
                        return CommandResult("Operator ${args[1]} has been added.",ErrorLevel.SUCCESS)
                    } catch (e: NumberFormatException) {
                        return CommandResult("Id invalid.",ErrorLevel.FAILED)
                    }
                }
                "delop" -> {
                    try {
                        Global.operators.remove(args[1].toLong())
                        return CommandResult("Operator ${args[1]} has been removed.",ErrorLevel.SUCCESS)
                    } catch (e: NumberFormatException) {
                        return CommandResult("Id invalid.",ErrorLevel.FAILED)
                    }
                }
                "lstop" -> {
                    return CommandResult(Global.operators.toString(),ErrorLevel.SUCCESS)
                }
                "lstallkw" -> {
                    return CommandResult(Global.replyDictionary.toString(),ErrorLevel.SUCCESS)
                }
                "ban" -> {
                    try {
                        Global.blacklist.add(args[1].toLong())
                        return CommandResult("${args[1]} has been banned.",ErrorLevel.SUCCESS)
                    } catch (e: NumberFormatException) {
                        return CommandResult("Id invalid.",ErrorLevel.FAILED)
                    }
                }
                "unban" -> {
                    try {
                        Global.blacklist.remove(args[1].toLong())
                        return CommandResult("${args[1]} has been removed from blacklist.",ErrorLevel.SUCCESS)
                    } catch (e: NumberFormatException) {
                        return CommandResult("Id invalid.",ErrorLevel.FAILED)
                    }
                }
                "lstbl" ->{
                    return CommandResult(Global.blacklist.toString(),ErrorLevel.SUCCESS)
                }

            }
            return CommandResult("",ErrorLevel.NOTFOUND)
        } catch (e: IndexOutOfBoundsException) {
            return CommandResult("Missing parameters.",ErrorLevel.FAILED)
        }
    }

    private fun toggle(arg: String):String {
        when (arg) {
            //TODO: Replace this with reflection.
            "print_info" -> {
                Global.printInfo = !Global.printInfo
                return "You have toggled debug information printing."
            }
            "accept_requests" -> {
                Global.acceptRequests =!Global.acceptRequests
                return "You have toggled request acceptation."
            }
            "service_state" -> {
                Global.suspendService = !Global.suspendService
                return "You have toggled service state."
            }
        }
        return ""
    }

}