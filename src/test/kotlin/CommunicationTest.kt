import network.Server
import org.junit.Test

class CommunicationTest {
    @Test
    fun serverTest(){
        Server().start(1080)
        Server().stop()
    }
    @Test
    fun sendMessageTest(){
        //To test message sending:
        //Replace 0L with your own id.
        //Client().sendMessageToFriend(0L,"hi")
    }
}