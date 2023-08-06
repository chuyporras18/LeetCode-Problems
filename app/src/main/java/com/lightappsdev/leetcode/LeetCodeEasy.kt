package com.lightappsdev.leetcode

import kotlin.system.measureNanoTime

fun main() {

}

fun twoSum(nums: IntArray, target: Int): IntArray {
    val map = mutableMapOf<Int, Int>()

    nums.forEachIndexed { index, int ->
        val x = target - int

        if (map.contains(x)) {
            return listOf(map[x]!!, index).toIntArray()
        }

        map[int] = index
    }

    return intArrayOf()
}

fun isPalindrome(x: Int) {

    fun asList(): Boolean {
        if (x < 0) return false

        val s = x.toString().toList()

        return s == s.reversed()
    }

    fun asString(): Boolean {
        val s = x.toString()
        for (i in s.indices) {
            if (s[i] != s[s.length - i - 1])
                return false
        }
        return true
    }
}

fun romanToInt(s: String): Int {
    val romanMap = mapOf(
        "I" to 1,
        "V" to 5,
        "X" to 10,
        "L" to 50,
        "C" to 100,
        "D" to 500,
        "M" to 1000,
        "IV" to 4,
        "IX" to 9,
        "XL" to 40,
        "XC" to 90,
        "CD" to 400,
        "CM" to 900
    )

    val list = arrayListOf<Int>()

    val specialCases = listOf("IV", "IX", "XL", "XC", "CD", "CM")

    specialCases.forEach { s1 ->
        list.add(Regex(s1).findAll(s).count() * romanMap[s1]!!)
    }

    list.add(specialCases.fold(s) { acc, patron -> acc.replace(patron, "") }
        .map { c -> romanMap[c.toString()]!! }.sum())

    return list.sum()
}

fun longestCommonPrefix(strs: Array<String>): String {
    if (strs.any { it.isEmpty() } || strs.isEmpty()) return ""

    var prefix = ""

    for (i in strs.minBy { it.length }.indices) {
        val matches = strs.map { s -> s.slice(0..i) }.toSet()
        val count = matches.size
        if (count == 1) {
            prefix = matches.first()
        }
    }

    return prefix
}

fun isValid(s: String): Boolean {
    val openCases = setOf('(', '[', '{')
    val closeCases = setOf(')', ']', '}')
    val closeMap = closeCases.zip(openCases).toMap()

    val openBrackets = mutableListOf<Char>()

    for (ch in s) {
        when {
            ch in openCases -> openBrackets.add(ch)
            ch in closeCases && closeMap[ch] == openBrackets.lastOrNull() ->
                openBrackets.removeAt(openBrackets.lastIndex)

            else -> return false
        }
    }

    return openBrackets.isEmpty()
}

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
    if (list1 != null && list2 != null) {
        return if (list1.`val` < list2.`val`) {
            list1.next = mergeTwoLists(list1.next, list2)
            list1
        } else {
            list2.next = mergeTwoLists(list1, list2.next)
            list2
        }
    }

    return list1 ?: list2
}

fun removeDuplicates(nums: IntArray): Int {
    nums.distinct().let { list ->
        list.forEachIndexed { index, i ->
            nums[index] = i
        }
        return list.size
    }
}

fun removeElement(nums: IntArray, `val`: Int): Int {
    var notEqual = 0
    for (num in nums) {
        if (num != `val`) {
            nums[notEqual++] = num
        }
    }
    return notEqual
}

fun strStr(haystack: String, needle: String): Int {
    for (index in 0..haystack.length - needle.length) {
        if (haystack.substring(index, index + needle.length) == needle) {
            return index
        }
    }
    return -1
}

fun searchInsert(nums: IntArray, target: Int): Int {
    var position = 0
    nums.forEachIndexed { index, i ->
        if (i < target) position = index + 1
        if (i == target) return index
    }
    return position
}

fun lengthOfLastWord(s: String): Int {
    return s.split(" ").last { it.isNotEmpty() }.length
}

fun plusOne(digits: IntArray): IntArray {
    val resultList = digits.toMutableList()
    var carry = 1

    for (i in digits.lastIndex downTo 0) {
        val sum = digits[i] + carry
        resultList[i] = sum % 10
        carry = sum / 10
    }

    if (carry == 1) {
        resultList.add(0, 1)
    }

    return resultList.toIntArray()
}

fun addBinary(a: String, b: String): String {
    val result = StringBuilder()
    var carry = 0
    var i = 0

    while (i < a.length || i < b.length || carry != 0) {
        var sum = carry

        if (i < a.length) sum += a[a.lastIndex - i] - '0'
        if (i < b.length) sum += b[b.lastIndex - i] - '0'

        carry = sum / 2
        result.append((sum % 2).toString())
        i++
    }

    return result.reverse().toString()
}

fun mySqrt(x: Int): Int {
    var left = 0
    var right = x

    while (left <= right) {
        val mid = left + (right - left) / 2
        val square = mid.toLong() * mid.toLong()

        when {
            square == x.toLong() -> return mid
            square < x.toLong() -> left = mid + 1
            else -> right = mid - 1
        }
    }

    return right
}

fun checkTime() {
    /*
    * Conclusions
    *
    * To iterate items add use them: forEach
    * To add items to a list: filter
    * */

    val numbers = (0..10_000_000).toList()

    println(
        "time filterForEach: ${
            measureNanoTime {
                //val list = arrayListOf<Int>()
                numbers.filter { it % 2 == 0 }.forEach {
                    //list.add(it * 2)
                    it * 2
                }
            } / 1e9f
        }s"
    )

    /*println(
        "time filter: ${
            measureNanoTime {
                val list = arrayListOf<Int>()
                list.addAll(numbers.filter { it % 2 == 0 })
            } / 1e9f
        }s"
    )*/

    println(
        "time forEach: ${
            measureNanoTime {
                //val list = arrayListOf<Int>()
                numbers.forEach {
                    if (it % 2 == 0) {
                        //list.add(it * 2)
                        it * 2
                    }
                }
            } / 1e9f
        }s"
    )

    println(
        "time map: ${
            measureNanoTime {
                //val list = arrayListOf<Int>()
                /*list.addAll(numbers.mapNotNull {
                    if (it % 2 == 0) {
                        it * 2
                    } else {
                        null
                    }
                })*/
                numbers.mapNotNull {
                    if (it % 2 == 0) {
                        it * 2
                    } else {
                        null
                    }
                }.forEach { it * 2 }
            } / 1e9f
        }s"
    )
}
