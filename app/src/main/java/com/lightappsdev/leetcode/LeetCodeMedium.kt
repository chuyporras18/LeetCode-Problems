package com.lightappsdev.leetcode

import kotlin.system.measureNanoTime

fun main() {
    println("time: ${measureNanoTime { println(permute()) } / 1e9f}s")
}

fun permute(list: MutableList<Int> = List(3) { it + 1 }.toMutableList()): List<MutableList<Int>> {
    val result = mutableListOf<MutableList<Int>>()

    if (list.size == 1) {
        return listOf(list.toMutableList())
    }

    for (i in list.indices) {
        val n = list.removeAt(0)
        val perms = permute(list)

        for (p in perms) {
            p.add(n)
        }

        result.addAll(perms)
        list.add(n)
    }

    return result
}

