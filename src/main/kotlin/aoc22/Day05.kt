package aoc22

import io.vavr.collection.HashMap
import io.vavr.collection.List
import io.vavr.collection.Map

object Day05 {

    fun calculateTopCrates(input: String, shouldMoveByOne: Boolean = true): String {
        val (stackLines, commands) = input.split("\n\n")
        val stacks = parseToStacks(stackLines)
        val appliedCommands = commands.trimEnd().lines()
            .fold(stacks) { acc, line -> applyCommand(acc, line, shouldMoveByOne) }
        return appliedCommands.toList()
            .sorted()
            .map { it._2.head() }
            .joinToString(separator = "")
    }

    private fun parseToStacks(input: String): Map<Int, List<Char>> {
        val parsed = input.lines().reversed()
            .flatMap { line ->
                line.withIndex()
                    .filter { it.value.isLetter() }
                    .map { (it.index / 4) + 1 to it.value }
            }
        return parsed.fold(HashMap.empty<Int, List<Char>>()) { acc, pair ->
            val currentStack = acc.getOrElse(pair.first, List.empty())
            acc.put(pair.first, currentStack.push(pair.second))
        }
    }

    private fun applyCommand(
        stacks: Map<Int, List<Char>>,
        command: String,
        shouldMoveByOne: Boolean = true
    ): Map<Int, List<Char>> {
        val (count, from, to) = command.split(' ')
            .mapNotNull { it.toIntOrNull() }
        val elements = stacks[from].get().take(count)
        val elementsSorted = if (shouldMoveByOne) elements else elements.reverse()
        val updatedFrom = stacks.put(from, stacks[from].get().drop(count))
        return updatedFrom.put(to, updatedFrom[to].get().pushAll(elementsSorted))
    }
}