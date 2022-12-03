package aoc22

import io.vavr.collection.Set
import io.vavr.kotlin.toVavrList
import java.lang.IllegalArgumentException

object Day03 {
    private const val LOWERCASE_OFFSET = 'a'.code - 1
    private const val UPPERCASE_OFFSET = 'A'.code - 27

    fun calcPrio(char: Char): Int {
        return when (char) {
            in 'a'..'z' -> char.code - LOWERCASE_OFFSET
            in 'A'..'Z' -> char.code - UPPERCASE_OFFSET
            else -> throw IllegalArgumentException()
        }
    }

    fun findCommonPrio(input: String): Int {
        val lines = input.trimEnd().lines()
        val prios = lines.map { line ->
            val parts = line.toList().toVavrList().splitAt(line.length / 2)
            val first = parts._1.toSet()
            val second = parts._2.toSet()
            val commonType = first.intersect(second).first()
            calcPrio(commonType)
        }
        return prios.sum()
    }

    fun findBadgePrio(input: String): Int {
        val lines = input.trimEnd().lines().toVavrList()
        val grouped = lines.grouped(3)
        val prios = grouped.map { group ->
            val charSets = group.map { it.toList().toVavrList().toSet() }
            val intersection = charSets.fold(charSets.first(), Set<Char>::intersect)
            calcPrio(intersection.first())
        }
        return prios.sum().toInt()
    }
}