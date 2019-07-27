package parser

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
    fun parseGroupCommand(content: String, groupId: Long):String {
        val args = content.split(" ")
        val command = args[0]
        when (command) {
            "addkw" -> {
                try {
                    val keyword = args[1]
                    val reply = content.substringAfter(" ").substringAfter(" ")
                    if (Global.replyDictionary[groupId]!=null) {
                        Global.replyDictionary[groupId]!![keyword] = reply
                    }else{
                        val initHashMap = HashMap<String, String>()
                        initHashMap[keyword] = reply
                        Global.replyDictionary[groupId] = initHashMap
                    }
                    return "Done. ($keyword,$reply)"
                }  catch (e: Exception) {
                    e.printStackTrace()
                    return "Failed to add keyword."
                }
            }
            "delkw" -> {
                try {
                    val param = content.split(" ")
                    val keyword = param[1]
                    if (Global.replyDictionary[groupId]!!.contains(keyword)) {
                        Global.replyDictionary[groupId]!!.remove(keyword)
                        return "Removed. ($keyword)"
                    }else{
                        return "\"${keyword}\" does not exist."
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    return "Failed to remove keyword."
                }
            }
            "lstkw" -> {
                    return Global.replyDictionary[groupId].toString()
            }
            "remindme" -> {
                try {
                    val sentence = content.drop(6)
                    if (sentence=="")
                    {
                        Global.reminders.remove(args[1].toLong())
                        return "Reminder of ${args[1].toLong()} has been removed."
                    }else{
                        Global.reminders.put(args[1].toLong(),sentence)
                        return "Done. (Reminder of ${args[1].toLong()})"
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    return "Add reminder failed."
                }
            }
            "syn" -> return "ack"
        }
        return ""
    }

    /**
     * parseSuperCommand
     * (Command from conversations and command)
     */
    fun parseSuperCommand(superCommand: String):String {
        //TODO: Return flexibly
        val args = superCommand.split(" ")
        try {
            when (args[0]) {
                "reset" -> {
                    Global.replyDictionary.clear()
                    return "Reset done."
                }
                "load" -> {
                    if (!Archive.loadKeywordsFromFile(File(args[1]))) return "Error loading configuration file."
                }
                "save" -> {
                    if (!Archive.saveKeywordsToFile(File(args[1]))) return "Error saving configuration file."
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
                    return toggle(args[1])
                }
                "addop" -> {
                    try {
                        Global.operators.add(args[1].toLong())
                        return "Operator ${args[1]} has been added."
                    } catch (e: Exception) {
                        when (e){
                            is NumberFormatException ->{
                                return "Id invalid."
                            }
                            is IndexOutOfBoundsException ->{
                                return "Missing parameters."
                            }
                        }
                    }
                }
                "delop" -> {
                    try {
                        Global.operators.remove(args[1].toLong())
                        return "Operator ${args[1]} has been removed."
                    } catch (e: Exception) {
                        when (e){
                            is NumberFormatException ->{
                                return "Id invalid."
                            }
                            is IndexOutOfBoundsException ->{
                                return "Missing parameters."
                            }
                        }
                    }
                }
                "lstop" -> {
                    return Global.operators.toString()
                }
                "lstallkw" -> {
                    return Global.replyDictionary.toString()
                }
                "ban" -> {
                    try {
                        Global.blacklist.add(args[1].toLong())
                        return "${args[1]} has been banned."
                    } catch (e: Exception) {
                        when (e){
                            is NumberFormatException ->{
                                return "Id invalid."
                            }
                            is IndexOutOfBoundsException ->{
                                return "Missing parameters."
                            }
                        }
                    }
                }
                "unban" -> {
                    try {
                        Global.blacklist.remove(args[1].toLong())
                        return "${args[1]} has been removed from blacklist."
                    } catch (e: Exception) {
                        when (e){
                            is NumberFormatException ->{
                                return "Id invalid."
                            }
                            is IndexOutOfBoundsException ->{
                                return "Missing parameters."
                            }
                        }
                    }
                }
                "lstbl" ->{
                    return Global.blacklist.toString()
                }
                //TODO: Complete the status query.
                "status" ->{
                    return ""
                }
            }
            return ""
        } catch (e: IndexOutOfBoundsException) {
            return "Parameters missing."
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