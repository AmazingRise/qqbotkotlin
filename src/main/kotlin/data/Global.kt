package data

object Global {
    //Cool down settings
    var coolDownSeconds: Int = 4
    val startTime = System.currentTimeMillis()
    var lastActivateTime = startTime - coolDownSeconds * 1000
    //Local port of the running server
    var localPort: Int = 8080
    //CoolQ frontend address. e.g.: 127.0.0.1:5700
    var remoteAddress: String = "172.16.237.233:5700"
    var replyDictionary: MutableMap<Long, MutableMap<String, String>> = HashMap<Long, MutableMap<String, String>>()
    var defaultArchiveLocation: String = "./archive.json"

    //The function "reminder"
    var reminders: MutableMap<Long, String> = HashMap<Long, String>()
    //Any messages sent by blacklists will be ignored
    var blacklist: MutableSet<Long> = mutableSetOf()
    //Operator, owning the permission to manage the server
    var operators: MutableList<Long> = mutableListOf(0L)

    //If printInfo is true, debug information will be printed out.
    var printInfo: Boolean = false
    //If enabled, all requests will be accepted.
    var acceptRequests: Boolean = false
    //If enabled, all the messages from non-op users will be ignored.
    var suspendService: Boolean = false
}