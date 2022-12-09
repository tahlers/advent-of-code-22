package aoc22

import java.lang.IllegalArgumentException
import kotlin.math.abs
import kotlin.math.sign

object Day09 {

    fun tailVisitedCounts(input: String, ropeLength: Int = 2): Int {
        val lines = input.trimEnd().lines()
        val parsed = lines.map { line ->
            val (dir, steps) = line.split(" ")
            dir.first() to steps.toInt()
        }
        val (initialDir, initialSteps) = parsed.first()
        val initialKnots = List(ropeLength) { Pos(0, 0) }
        val visited = simulate(initialDir, initialSteps, parsed.drop(1), initialKnots)
        return visited.size
    }

    private tailrec fun simulate(
        currentDir: Char,
        remainingSteps: Int,
        remaining: List<Pair<Char, Int>>,
        knots: List<Pos>,
        visited: Set<Pos> = emptySet(),
    ): Set<Pos> {
        return if (remainingSteps < 1) {
            if (remaining.isEmpty()) {
                visited
            } else {
                val first = remaining.first()
                simulate(first.first, first.second, remaining.drop(1), knots, visited)
            }
        } else {
            val updatedHead = updateHead(knots.first(), currentDir)
            val updatedKnots = updateTail(updatedHead, knots.drop(1))
            val updatedVisited = visited + updatedKnots.last()
            simulate(currentDir, remainingSteps - 1, remaining, updatedKnots, updatedVisited)
        }
    }

    private fun updateHead(current: Pos, char: Char): Pos {
        return when (char) {
            'R' -> Pos(current.x + 1, current.y)
            'L' -> Pos(current.x - 1, current.y)
            'U' -> Pos(current.x, current.y + 1)
            'D' -> Pos(current.x, current.y - 1)
            else -> throw IllegalArgumentException()
        }
    }

    private fun updateTail(head: Pos, tail: List<Pos>): List<Pos> {
        return tail.runningFold(head) { acc, pos ->
            updateTail(acc, pos)
        }
    }

    private fun updateTail(head: Pos, tail: Pos): Pos {
        return if (abs(head.x - tail.x) <= 1 && abs(head.y - tail.y) <= 1) {
            tail
        } else {
            val newX = tail.x + (head.x - tail.x).sign
            val newY = tail.y + (head.y - tail.y).sign
            Pos(newX, newY)
        }
    }
}