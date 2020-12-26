package ru.emkn.kotlin

class Matrix<T> (val xSize: Int, val ySize: Int, val array: Array<Array<T>>) {

    companion object {

        inline operator fun <reified T> invoke() = Matrix(0, 0, Array(0) { emptyArray<T>() })

        inline operator fun <reified T> invoke(xWidth: Int, yWidth: Int) =
            Matrix(xWidth, yWidth, Array(xWidth) { arrayOfNulls<T>(yWidth) })

        inline operator fun <reified T> invoke(xWidth: Int, yWidth: Int, operator: (Int, Int) -> (T)): Matrix<T> {
            val array = Array(xWidth) { elem ->
                Array(yWidth) { operator(elem, it) }
            }
            return Matrix(xWidth, yWidth, array)
        }
    }

    operator fun get(x: Int, y: Int): T {
        return array[x][y]
    }

    operator fun set(x: Int, y: Int, t: T) {
        array[x][y] = t
    }

    inline fun forEachInRow(row: Int, operation: (T) -> Unit) {
        array[row].forEach { operation.invoke(it) }
    }
}