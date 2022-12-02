package aoc22

import io.vavr.collection.List
import io.vavr.kotlin.toVavrList
import kotlin.math.max

object Day01 {
    fun calculateHighestCalories(input: String): Int {
        val lines = input.lines().toVavrList()
        val caloriesList = lines.map { it.toIntOrNull() ?: 0 }
        return calcHighest(caloriesList, 0, 0)
    }

    private tailrec fun calcHighest(inputList: List<Int>, currentCount: Int, highest: Int): Int {
        if (inputList.isEmpty) return highest
        return when (val head = inputList.head()) {
            0 -> calcHighest(inputList.tail(), 0, max(currentCount, highest))
            else -> calcHighest(inputList.tail(), currentCount + head, highest)
        }
    }

    fun calculateTopThree(input: String): Int {
        val lines = input.lines().toVavrList()
        val caloriesList = lines.map { it.toIntOrNull() ?: 0 }
        val elfCalories = toCalPerElf(caloriesList)
        return elfCalories.sorted().reverse().take(3).sum().toInt()
    }

    private tailrec fun toCalPerElf(
        input: List<Int>,
        currentCount: Int = 0,
        resultList: List<Int> = List.empty()
    ): List<Int> {
        if (input.isEmpty) return resultList.prepend(currentCount)
        val head = input.head()
        val tail = input.tail()
        return when (head) {
            0 -> toCalPerElf(tail, 0, resultList.prepend(currentCount))
            else -> toCalPerElf(tail, currentCount + head, resultList)
        }
    }
}