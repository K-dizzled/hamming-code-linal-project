package ru.emkn.kotlin

import kotlin.random.Random

fun main() {
    val inputString = "Look\n" +
            "If you had\n" +
            "One shot\n" +
            "Or one opportunity\n" +
            "To seize everything you ever wanted\n" +
            "In one moment\n" +
            "Would you capture it\n" +
            "Or just let it slip?"
    println("Исходная строка:\n$inputString\n")

    var binaryString = convertStringToBinary(inputString)
    println("Переведенная в бинарную строка: ${binaryString.joinToString("")}\n")

    val blocks = splitIntoBlocks(binaryString).toMutableList()
    blocks.replaceAll { encodeBlock(it) }
    println("Закодированная строка: ${blocks.flatten().joinToString("")}\n")

    blocks.replaceAll { changeRandomBitInBlock(it) }
    println("Поврежденная строка: ${blocks.flatten().joinToString("")}\n")

    blocks.replaceAll { decodeBlock(it) }
    binaryString = connectBlocksTogether(blocks)
    println("Восстановленная строка: ${binaryString.joinToString("")}\n")

    val outputString = convertBinaryToString(binaryString)
    println("Итоговая строка:\n$outputString")
}

fun decodeBlock(block: List<Int>): List<Int> {
    val correctedBlock = correctBlock(block)
    val decodedBlock = mutableListOf<Int>()

    decodedBlock.add(correctedBlock[6])
    decodedBlock.add(correctedBlock[5])
    decodedBlock.add(correctedBlock[4])
    decodedBlock.add(correctedBlock[2])

    return decodedBlock
}

fun encodeBlock(block: List<Int>): List<Int> {
    val matrixG = Matrix(
        4, 7, arrayOf(
            arrayOf(1, 0, 1, 1),
            arrayOf(1, 1, 0, 1),
            arrayOf(0, 0, 0, 1),
            arrayOf(1, 1, 1, 0),
            arrayOf(0, 0, 1, 0),
            arrayOf(0, 1, 0, 0),
            arrayOf(1, 0, 0, 0)
        )
    )

    return multiplyMatrix(block, matrixG)
}

fun changeRandomBitInBlock(block: List<Int>): List<Int> {
    val new = block.toMutableList()
    val rand = Random.nextInt(0, new.size)
    new[rand] = (new[rand] + 1) % 2

    return new
}
