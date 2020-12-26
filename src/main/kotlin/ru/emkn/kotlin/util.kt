package ru.emkn.kotlin

fun checkSum(block: List<Int>): List<Int> {
    val matrixH = Matrix(
        7, 3, arrayOf(
            arrayOf(0, 0, 0, 1, 1, 1, 1),
            arrayOf(0, 1, 1, 0, 0, 1, 1),
            arrayOf(1, 0, 1, 0, 1, 0, 1)
        )
    )

    return multiplyMatrix(block, matrixH)
}

fun multiplyMatrix(block: List<Int>, matrix: Matrix<Int>): List<Int> {
    var multRow: Int
    var index: Int
    val result = mutableListOf<Int>()
    for (i in 0 until matrix.ySize) {
        multRow = 0
        index = 0
        matrix.forEachInRow(i) { multRow += it * block[index]; index++ }
        result.add(multRow % 2)
    }
    return result.toList()
}

fun convertStringToBinary(input: String): List<Int> {
    val bytes = input.toByteArray()
    val binary = mutableListOf<Int>()
    for (b in bytes) {
        var bValue = b.toInt()
        for (i in 0..7) {
            binary.add(if (bValue and 128 == 0) 0 else 1)
            bValue = bValue shl 1
        }
    }
    return binary.toList()
}

fun convertBinaryToString(binary: List<Int>): String {
    val letters = binary.joinToString("")
    var s = ""
    var index = 0
    while (index < letters.length) {
        val temp = letters.substring(index, index + 8)
        val num = temp.toInt(2)
        val letter = num.toChar()
        s += letter
        index += 8
    }
    return s
}

fun detectErrorInBlock(checkSum: List<Int>): Int {
    val string = checkSum.joinToString("")

    return string.toInt(radix = 2) - 1
}

fun correctBlock(block: List<Int>): List<Int> {
    val new = block.toMutableList()
    val checkSum = checkSum(new)
    val row = detectErrorInBlock(checkSum)

    if (row != -1)
        new[row] = (new[row] + 1) % 2

    return new
}

fun splitIntoBlocks(binaryString: List<Int>) = binaryString.chunked(4)

fun connectBlocksTogether(blocks: List<List<Int>>): List<Int> = blocks.flatten()