import org.junit.Assert.assertEquals
import org.junit.Test
import parser.command.CommandInterpreter
import util.Archive
import java.io.File
import java.util.logging.Level
import java.util.logging.Logger

class ArchiveTest {
    @Test
    fun simpleArchiveTest(){
        val logger = Logger.getLogger("keywordTestLogger")
        logger.level = Level.INFO
        val interpreter = CommandInterpreter()

        //Saving test
        interpreter.parseGroupCommand("addkw hello hi",0L)
        Archive.saveKeywordsToFile(File("1.json"))
        assertEquals("""{"0":{"hello":"hi"}}""",File("1.json").bufferedReader().use { it.readText() })

        //Loading test
        interpreter.parseGroupCommand("delkw hello", 0L)
        Archive.loadKeywordsFromFile(File("1.json"))
        assertEquals("{hello=hi}", CommandInterpreter().parseGroupCommand("lstkw",0L))

        //Delete temporary file
        File("1.json").delete()
    }
}