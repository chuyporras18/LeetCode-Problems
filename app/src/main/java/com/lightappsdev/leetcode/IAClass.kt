package com.lightappsdev.leetcode

fun main() {
    val backpack =
        mutableListOf(
            Pair("C1", 100),
            Pair("C2", 155),
            Pair("C3", 50),
            Pair("C4", 112),
            Pair("C5", 70),
            Pair("C6", 80),
            Pair("C7", 60),
            Pair("C8", 118),
            Pair("C9", 110),
            Pair("C10", 55)
        )

    println(IAClass().backpack(backpack, 0, 700))
}

class IAClass {

    fun cube(n: Int): Int {
        return n * n * n
    }

    fun factorial(n: Int): Int {
        var factorial = 1
        for (i in 1..n) {
            factorial *= i
        }
        return factorial
    }

    fun patternCount(pattern: String, string: String): Int {
        var count = 0
        for (i in 0 until string.length - pattern.length) {
            if (string.slice(i..i + pattern.lastIndex) == pattern) {
                count++
            }
        }
        return count
    }

    fun expressionDeep(expression: String): Int? {
        val open = expression.count { it == '(' }
        val close = expression.count { it == ')' }
        return if (open == close) close else null
    }

    private val result = mutableListOf<Pair<String, Int>>()

    fun backpack(backpack: MutableList<Pair<String, Int>>, i: Int, max: Int): List<String>? {
        if (i >= backpack.size) {
            return result.map { it.first }
        }

        val n = backpack[i]

        if (n.second <= max - result.sumOf { it.second }) {
            
            result.add(n)

            return backpack(backpack, i + 1, max)
        }

        result.remove(n)

        return backpack(backpack, i + 1, max)
    }
}