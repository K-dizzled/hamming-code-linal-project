import org.junit.jupiter.api.*
import ru.emkn.kotlin.convertBinaryToString
import ru.emkn.kotlin.convertStringToBinary
import java.lang.IllegalArgumentException
import java.time.Duration
import java.util.stream.IntStream
import java.util.stream.Stream
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

fun getRandomString(length: Int) : String {
    val allowedChars = ('a'..'z')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

class HammingTests {
    @Test
    fun `simple test`() {
    }

    @Test
    fun `illegal argument test`() {
        assertFailsWith(IllegalArgumentException::class) {

        }
    }

    @Test
    fun `timeout test`() {
        val res = assertTimeoutPreemptively(Duration.ofSeconds(5)) {
            //slowFib(50)
        }
        assertEquals(20365011074L, res)
    }

    @TestFactory
    fun `multiple test`(): Stream<DynamicTest> {
        return IntStream.range(0, 99).mapToObj { n ->
            DynamicTest.dynamicTest("Test converting for length $n") {
                val temp = getRandomString(n)
                assertEquals(
                    temp,
                    convertBinaryToString(
                        convertStringToBinary(temp)
                    )
                )
            }
        }
    }
}