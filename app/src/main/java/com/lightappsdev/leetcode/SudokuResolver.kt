package com.lightappsdev.leetcode

import kotlin.system.measureTimeMillis

fun main() {
    println("sudokuSolver: ${measureTimeMillis { SudokuResolver().sudokuResolver() } / 1000f} s")
}

class SudokuResolver {

    private fun generateSudoku(): List<MutableList<Int>> {

        fun mapToList(map: Map<Int, Int>): MutableList<Int> {
            return (0 until 9).map { map.getOrElse(it) { 0 } }.toMutableList()
        }

        return listOf(
            mapToList(mapOf(0 to 3, 1 to 8, 3 to 1, 6 to 6, 7 to 7)),
            mapToList(mapOf(5 to 4, 6 to 2)),
            mapToList(mapOf(2 to 6)),
            mapToList(mapOf(0 to 7, 1 to 5, 3 to 3, 7 to 8)),
            mapToList(mapOf(0 to 9)),
            mapToList(mapOf(4 to 1, 8 to 3)),
            mapToList(mapOf(0 to 5, 1 to 6, 4 to 2, 6 to 7)),
            mapToList(mapOf(2 to 9, 3 to 5)),
            mapToList(mapOf(2 to 1, 7 to 6)),
        )
    }

    fun sudokuResolver(formatResult: Boolean = true) {

        val sudoku = generateSudoku().also { println("sudoku:\n${formatSudoku(it)}") }
        var quadrants =
            sudokuToQuadrants(sudoku).also { println("quadrants:\n${formatSudoku(it)}") }

        fun resolve(): List<List<Int>>? {
            if (isSudokuSolved(sudoku)) {
                return sudoku
            }

            val (i, j) = leastOptionsCells(sudoku)

            if (i == -1 || j == -1) {
                return sudoku
            }

            for (n in (1..9)) {
                if (isSudokuNumberValid(sudoku, quadrants, i, j, n)) {
                    putSudokuNumber(sudoku, i, j, n)

                    quadrants = sudokuToQuadrants(sudoku)

                    if (resolve() != null) {
                        return sudoku
                    }

                    putSudokuNumber(sudoku, i, j, 0)

                    quadrants = sudokuToQuadrants(sudoku)
                }
            }

            return null
        }

        val result = resolve()

        if (formatResult) {
            println("solution:\n" + formatSudoku(result.orEmpty()))
        } else {
            println(result)
        }
    }

    private fun formatSudoku(sudoku: List<List<Int>>): String {
        val stringBuilder = StringBuilder()
        sudoku.forEachIndexed { i1, l1 ->
            val l = l1.chunked(3).joinToString(" | ")
            if (i1 % 3 == 0 && i1 != 0) {
                stringBuilder.append(l.map { "-" }.joinToString("") + "\n")
            }

            stringBuilder.append(l + "\n")
        }
        return stringBuilder.toString()
    }

    private fun isSudokuSolved(sudoku: List<List<Int>>): Boolean {
        return !sudoku.any { it.contains(0) }
    }

    private fun isSudokuNumberValid(
        sudoku: List<List<Int>>,
        quadrants: List<List<Int>>,
        i: Int,
        j: Int,
        n: Int
    ): Boolean {
        if (sudoku[i][j] != 0) return false

        val board = getSudokuQuadrants(quadrants, i, j)

        val square = !board.contains(n)

        val horizontal = !sudoku[i].contains(n)

        val vertical = !sudoku.map { it[j] }.contains(n)

        return square && horizontal && vertical
    }

    private fun sudokuToQuadrants(sudoku: List<List<Int>>): List<List<Int>> {
        return sudoku.chunked(3).flatMap { quadrant ->
            val chunked = quadrant.flatMap { list ->
                list.chunked(3)
            }

            val modules =
                (0..chunked.size).associateBy({ it % 3 }, { emptyList<Int>() }).toMutableMap()

            chunked.forEachIndexed { index, ints ->
                modules[index % 3] = modules[index % 3].orEmpty() + ints
            }

            modules.values
        }
    }

    private fun leastOptionsCells(sudoku: List<List<Int>>): Pair<Int, Int> {
        val row = sudoku.withIndex().filter { it.value.contains(0) }
            .minBy { (_, int) -> int.count { it == 0 } }.index
        val colum = sudoku[row].indexOf(0)
        return Pair(row, colum)
    }

    private fun getSudokuQuadrants(board: List<List<Int>>, i: Int = 0, j: Int = 6): List<Int> {
        val index = (i / 3) * 3 + j / 3
        return board[index]
    }

    private fun putSudokuNumber(sudoku: List<MutableList<Int>>, i: Int, j: Int, n: Int) {
        sudoku[i][j] = n
    }
}