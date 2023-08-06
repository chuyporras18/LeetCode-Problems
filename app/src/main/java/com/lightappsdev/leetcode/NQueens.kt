package com.lightappsdev.leetcode

import kotlin.system.measureNanoTime

fun main() {
    println(
        "nQueens: ${
            measureNanoTime {
                val size = 8
                val board = List(size) { List(size) { " " }.toMutableList() }
                println(NQueens().nQueensResolver(board, 0, 0)?.joinToString("\n"))
            } / 1e9f
        }s"
    )
}

class NQueens {

    companion object {
        private const val QUEEN: String = "Q"
    }

    fun nQueensResolver(board: List<MutableList<String>>, i: Int, j: Int): List<List<String>>? {
        if (i == board.size) {
            return board
        } else if (j == board.size) {
            return nQueensResolver(board, i + 1, 0)
        } else {
            if (isQueenPlaceable(board, i, j)) {
                placeQueen(board, i, j)

                if (nQueensResolver(board, i, j) != null) {
                    return board
                }

                removeQueen(board, i, j)
            }

            return nQueensResolver(board, i, j + 1)
        }
    }

    fun generateDiagonals(
        board: List<List<String>>,
        i: Int,
        j: Int
    ): Pair<List<String>, List<String>> {

        fun rightDown(): List<String> {
            val list = mutableListOf<String>()
            var (k, z) = Pair(i, j)

            while (k < board.size - 1 && z < board.size - 1) {
                list.add(board[k + 1][z + 1])
                k++
                z++
            }

            return list
        }

        fun leftUp(): List<String> {
            val list = mutableListOf<String>()
            var (k, z) = Pair(i, j)

            while (k > 0 && z > 0) {
                list.add(board[k - 1][z - 1])
                k--
                z--
            }

            return list
        }

        fun leftDown(): List<String> {
            val list = mutableListOf<String>()
            var (k, z) = Pair(i, j)

            while (k < board.size - 1 && z > 0) {
                list.add(board[k + 1][z - 1])
                k++
                z--
            }

            return list
        }

        fun rightUp(): List<String> {
            val list = mutableListOf<String>()
            var (k, z) = Pair(i, j)

            while (k > 0 && z < board.size - 1) {
                list.add(board[k - 1][z + 1])
                k--
                z++
            }

            return list
        }

        return Pair(leftUp() + rightDown(), leftDown() + rightUp())
    }

    fun placeQueen(board: List<MutableList<String>>, i: Int, j: Int) {
        board[i][j] = QUEEN
    }

    fun removeQueen(board: List<MutableList<String>>, i: Int, j: Int) {
        board[i][j] = " "
    }

    fun isQueenPlaceable(board: List<List<String>>, i: Int, j: Int): Boolean {
        val vertical = board.map { it[j] }.contains(QUEEN)
        val horizontal = board[i].contains(QUEEN)
        val diagonals = generateDiagonals(board, i, j)
        val diagonal = diagonals.first.contains(QUEEN) || diagonals.second.contains(QUEEN)

        return !vertical && !horizontal && !diagonal
    }
}