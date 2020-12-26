import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import ru.emkn.kotlin.convertBinaryToString
import ru.emkn.kotlin.convertStringToBinary
import ru.emkn.kotlin.decodeBlock
import ru.emkn.kotlin.encodeBlock
import java.util.stream.IntStream
import java.util.stream.Stream
import kotlin.random.Random
import kotlin.test.assertEquals

fun getRandomString(length: Int) : String {
    val allowedChars = ('a'..'z')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

class HammingTests {
//    @Test
//    fun `simple test`() {
//        for (i in 1..30) {
//
//        }
//    }

//    @Test
//    fun `illegal argument test`() {
//        assertFailsWith(IllegalArgumentException::class) {
//
//        }
//    }

    @TestFactory
    fun `encode - decode without mistakes test`(): Stream<DynamicTest> {
        return IntStream.range(0, 99).mapToObj { n ->
            DynamicTest.dynamicTest("Test encode-decode number $n") {
                val temp = listOf(
                    Random.nextInt(0, 2),
                    Random.nextInt(0, 2),
                    Random.nextInt(0, 2),
                    Random.nextInt(0, 2)
                )
                println(temp)
                assertEquals(
                    listOf(0, 0, 0),
                    decodeBlock(encodeBlock(temp))
                )
            }
        }
    }

    @TestFactory
    fun `string - binary convert test`(): Stream<DynamicTest> {
        return IntStream.range(0, 99).mapToObj { n ->
            DynamicTest.dynamicTest("Test converting for length $n") {
                val temp = getRandomString(n)
                print(temp)
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