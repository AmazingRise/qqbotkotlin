package data

data class Message(
    val font: Int,
    var message: String,
    val message_id: Int,
    val message_type: String,
    val group_id: Long,
    val post_type: String,
    val raw_message: String,
    val self_id: Long,
    val sender: Sender,
    val sub_type: String,
    val time: Int,
    val user_id: Long,
    val notice_type: String
){/*
    fun isFromOp(): Boolean {
        return this.user_id in ops;
    }
*/}
