package network
import data.Global
import data.PromptLogType
import khttp.get
import util.Prompt
import java.net.URLEncoder

object Client {
    fun sendMessageToGroup(groupId: Long, content: String): Boolean {
        return try {
            get("http://${Global.remoteAddress}/send_msg?group_id=$groupId&message=$content")
            true
        } catch (e: NullPointerException) {
            Prompt.echo("Failed to send message due to null remote address.", PromptLogType.FAILED)
            false
        }
    }
    fun sendMessageToFriend(friendId: Long, content: String): Boolean {
        return try {
            get("http://${Global.remoteAddress}/send_msg?user_id=$friendId&message=$content")
            true
        } catch (e: NullPointerException) {
            Prompt.echo("Failed to send message due to null remote address.", PromptLogType.FAILED)
            false
        }
    }
    fun sendMessageTo(content: String,groupId:Long,friendId: Long): Boolean {
        if (content=="") return true
        return try {
            Prompt.echo("Reply to $friendId @ $groupId: $content")
            val reply = URLEncoder.encode(content.replace("=",":"), "UTF-8")
            Prompt.echo("Encoded:$reply")
            var parameters = "message=$reply"
            if (groupId==0L){
                parameters += "&user_id=$friendId"
            }else{
                parameters += "&group_id=$groupId"
            }
            get("http://${Global.remoteAddress}/send_msg?$parameters")

            true
        } catch (e: NullPointerException) {
            Prompt.echo("Failed to send message due to null remote address.", PromptLogType.FAILED)
            e.printStackTrace()
            false
        }
    }
}