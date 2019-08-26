import org.junit.Test
import parser.RawRequestParser
import util.Prompt

class ParserTest {
    @Test
    fun newMemberTest(){
        val requestBody = """{"group_id":677877684,"notice_type":"group_increase","operator_id":745584302,"post_type":"notice","self_id":2484791876,"sub_type":"invite","time":1566661826,"user_id":2491159925}"""
        val parserResponse = RawRequestParser().parse(requestBody)
        Prompt.echo(parserResponse.reply)
    }
}