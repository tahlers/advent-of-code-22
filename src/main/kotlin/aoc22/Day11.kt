package aoc22

import io.vavr.collection.Map
import io.vavr.collection.Vector
import io.vavr.kotlin.toVavrMap
import io.vavr.kotlin.toVavrStream

object Day11 {

    sealed interface Operation {
        fun calc(input: Long): Long

        data class Add(val value: Long): Operation {
            override fun calc(input: Long) = value + input
        }

        data class Mul(val value: Long): Operation {
            override fun calc(input: Long) = value * input
        }

        object Quad: Operation {
            override fun calc(input: Long) = input * input
        }
    }

    data class Monkey(
        val id: Int,
        val startingItems: Vector<Long>,
        val operation: Operation,
        val divisor: Long,
        val successTarget: Int,
        val failureTarget: Int,
        val inspectionCount: Long = 0,
        val worryEnabled: Boolean = true
    ) {
        fun addItem(item: Long) = copy(startingItems = startingItems.append(item))
        fun finishInspection() = copy(
            startingItems = Vector.empty(),
            inspectionCount = inspectionCount + startingItems.size())
    }

    fun calculateMonkeyBusinessLevel(input: String, count: Int, worryEnabled: Boolean = true): Long {
        val monkeyLines = input.trimEnd().split("\n\n")
        val monkeys = monkeyLines.map { parseMonkey(it, worryEnabled) }
        val monkeyMap = monkeys.associateBy { it.id }.toVavrMap()
        val rounds = (1 .. count).runningFold(monkeyMap) { acc, _ ->
            round(acc)
        }
        val mostActive = rounds.last().values()
            .sortedByDescending { it.inspectionCount }
            .take(2)
        return mostActive.map { it.inspectionCount }.reduce {acc, i -> acc * i }
    }

    private fun round(monkeys: Map<Int, Monkey>): Map<Int, Monkey> {
        return monkeys.keySet().fold(monkeys) { currentMonkeys, id ->
            takeTurn(currentMonkeys.get(id).get(), currentMonkeys)
        }
    }

    private fun takeTurn(current: Monkey, monkeys: Map<Int, Monkey>): Map<Int, Monkey> {
        val worryDivisor = if (current.worryEnabled) 3 else 1
        val moduloOperator = monkeys.values().map { it.divisor }.fold(1) {a, b -> a * b}
        val processed = current.startingItems.map { item ->
            val opResult = current.operation.calc(item)
            if (current.worryEnabled) opResult / worryDivisor else opResult % moduloOperator
        }
        val updatedMonkeys = processed.fold(monkeys) { currentMonkeys, item ->
            val targetMonkey = if ((item % current.divisor) == 0L) current.successTarget else current.failureTarget
            currentMonkeys.put(targetMonkey, currentMonkeys.get(targetMonkey).get().addItem(item))
        }
        return updatedMonkeys.put (current.id, current.finishInspection())
    }

    private fun parseMonkey(input: String, worryEnabled: Boolean): Monkey {
        val lines = input.lines().map { it.trim() }
        val monkeyId = lines[0].substringAfter("Monkey ").first().digitToInt()
        val startingItems = lines[1].substringAfter("Starting items: ")
            .split(", ")
            .map { it.toLong() }
            .toVavrStream().toVector()
        val opLine = lines[2].substringAfter("= ")
        val op = if (opLine == "old * old") {
            Operation.Quad
        } else {
            val value = opLine.substring(6).toLong()
            if (opLine[4] == '*') Operation.Mul(value) else Operation.Add(value)
        }
        val divisor = lines[3].substringAfter(" by ").toLong()
        val successTarget = lines[4].substringAfter("monkey ").toInt()
        val failureTarget = lines[5].substringAfter("monkey ").toInt()
        return Monkey(monkeyId, startingItems, op, divisor, successTarget, failureTarget, worryEnabled = worryEnabled)
    }
}