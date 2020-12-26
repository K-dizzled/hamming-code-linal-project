import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import ru.emkn.kotlin.*
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
    @TestFactory
    fun `integration test`(): Stream<DynamicTest> {
        return IntStream.range(0, 99).mapToObj { n ->
            DynamicTest.dynamicTest("Test converting for length $n") {
                val inpString = getRandomString(n)
                print(inpString)
                val blocks =
                    splitIntoBlocks(convertStringToBinary(inpString)).toMutableList()
                blocks.replaceAll { encodeBlock(it) }
                blocks.replaceAll { changeRandomBitInBlock(it) }
                blocks.replaceAll { decodeBlock(it) }
                val outputString =
                    convertBinaryToString(connectBlocksTogether(blocks))

                assertEquals(inpString, outputString)
            }
        }
    }

    @TestFactory
    fun `encode - decode with mistakes test`(): Stream<DynamicTest> {
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
                    temp,
                    decodeBlock(changeRandomBitInBlock(encodeBlock(temp)))
                )
            }
        }
    }

    @TestFactory
    fun `correct error test`(): Stream<DynamicTest> {
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
                    encodeBlock(temp),
                    correctBlock(changeRandomBitInBlock(encodeBlock(temp)))
                )
            }
        }
    }

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
                    checkSum(encodeBlock(temp))
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