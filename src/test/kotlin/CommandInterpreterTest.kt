import org.junit.Test
import parser.CommandInterpreter
import java.util.logging.Level
import java.util.logging.Logger

class CommandInterpreterTest {
    @Test
    fun keywordTest(){
        val logger = Logger.getLogger("keywordTestLogger")
        logger.level = Level.INFO
        logger.info(CommandInterpreter().parseGroupCommand("delkw hello",123456L))
        logger.info(CommandInterpreter().parseGroupCommand("addkw hello hi",123456L))
        logger.info(CommandInterpreter().parseGroupCommand("lstkw",123456L))
        logger.info(CommandInterpreter().parseGroupCommand("delkw hello",123456L))
        logger.info(CommandInterpreter().parseGroupCommand("lstkw",123456L))
        logger.info(CommandInterpreter().parseGroupCommand("delkw hello",123456L))
    }

    @Test
    fun resetTest(){
        val logger = Logger.getLogger("keywordTestLogger")
        logger.level = Level.INFO
        logger.info(CommandInterpreter().parseGroupCommand("delkw hello",123456L))
        logger.info(CommandInterpreter().parseGroupCommand("addkw hello hi",123456L))
        logger.info(CommandInterpreter().parseGroupCommand("lstkw",123456L))
        logger.info(CommandInterpreter().parseGroupCommand("delkw hello",123456L))
        logger.info(CommandInterpreter().parseGroupCommand("lstkw",123456L))
        logger.info(CommandInterpreter().parseGroupCommand("delkw hello",123456L))
    }
}