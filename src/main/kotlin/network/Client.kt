package network
import data.Global
import data.LogType
import khttp.get
import util.Prompt
import java.lang.NullPointerException

class Client {
    fun sendMessageToGroup(groupId: Long, content: String): Boolean {
        return try {
            get("http://${Global.remoteAddress}/send_msg?group_id=$groupId&message=$content")
            true
        } catch (e: NullPointerException) {
            Prompt.echo("Failed to send message due to null remote address.", LogType.FAILED)
            false
        }
    }
    fun sendMessageToFriend(friendId: Long, content: String): Boolean {
        return try {
            get("http://${Global.remoteAddress}/send_msg?user_id=$friendId&message=$content")
            true
        } catch (e: NullPointerException) {
            Prompt.echo("Failed to send message due to null remote address.", LogType.FAILED)
            false
        }
    }
}