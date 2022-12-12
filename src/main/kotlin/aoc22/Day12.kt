package aoc22

import io.vavr.collection.List
import io.vavr.collection.Set
import io.vavr.kotlin.hashSet
import io.vavr.kotlin.toVavrStream
import kotlin.math.abs

object Day12 {

    data class Pos(val x: Int, val y: Int) {
        fun distance(other: Pos) = abs(other.x - x) + abs(other.y - y)
    }

    data class Landscape(
        val grid: Map<Pos, Int>, val maxX: Int, val maxY: Int, val starts: Set<Pos>, val ends: Set<Pos>
    ) {
        fun neighbours(pos: Pos): Set<Pos> {
            val candidates = hashSet(
                Pos(pos.x - 1, pos.y),
                Pos(pos.x + 1, pos.y),
                Pos(pos.x, pos.y - 1),
                Pos(pos.x, pos.y + 1),
            )
            val positionFiltered = candidates.filter { it in grid }
            val allowedHeight = grid.getValue(pos) + 1
            return positionFiltered.filter { grid.getValue(it) <= allowedHeight }
        }
    }

    data class Node(val pos: Pos, val weight: Int, val predecessor: Node?) {

        override fun toString(): String {
            return "Node(pos=$pos, weight=$weight)"
        }
    }

    fun calcMinStepsToEnd(input: String): Int {
        val landscape = parseLandscape(input)
        val startNodes = landscape.starts.map { Node(it, 0, null) }
        val endNode = search(landscape, startNodes.toList(), hashSet())
        return endNode?.let { pathLength(it, 0) } ?: 0
    }

    fun calcMinStepsBack(input: String): Int {
        val landscape = parseLandscape(input)
        val additionalStarts = landscape.grid.filterValues { it == 0 }.keys
        val newLandscape = landscape.copy(starts = landscape.starts.addAll(additionalStarts))
        val startNodes = newLandscape.starts.map { Node(it, 0, null) }
        val endNode = search(newLandscape, startNodes.toList(), hashSet())
        return endNode?.let { pathLength(it, 0) } ?: 0
    }


    private tailrec fun pathLength(current: Node, currentSteps: Int): Int {
        if (current.predecessor == null) return currentSteps
        return pathLength(current.predecessor, currentSteps + 1)
    }

    private tailrec fun search(landscape: Landscape, open: List<Node>, closed: Set<Pos>): Node? {
        if (open.isEmpty) return null
        val currentNode = open.head()
        if (currentNode.pos in landscape.ends) return currentNode
        val updatedClosed = closed.add(currentNode.pos)
        val updatedOpen = expand(landscape, currentNode, open.tail(), updatedClosed)
        return search(landscape, updatedOpen, updatedClosed)
    }

    private fun expand(
        landscape: Landscape, current: Node, open: List<Node>, closed: Set<Pos>
    ): List<Node> {
        val successorPos = landscape.neighbours(current.pos)
        return successorPos.fold(open) { currentOpen, pos ->
            if (pos in closed) return@fold currentOpen
            val newWeight = current.weight + landscape.ends.map { pos.distance(it) }.min().get()
            val nodeFromOpen = currentOpen.find { it.pos == pos }
            if (nodeFromOpen.isDefined && nodeFromOpen.get().weight <= newWeight) return@fold currentOpen
            val newNode = Node(pos, newWeight, current)
            currentOpen.append(newNode).sortBy { it.weight }
        }
    }

    private fun parseLandscape(input: String): Landscape {
        val lines = input.lines()
        val cells = lines.flatMapIndexed { y, line ->
            line.mapIndexed { x, c -> Pos(x, y) to c }
        }.toMap()
        val heightMap = cells.mapValues { entry ->
            when (entry.value) {
                'S' -> 0
                'E' -> 25
                else -> entry.value.code - 'a'.code
            }
        }
        return Landscape(
            heightMap,
            lines[0].length,
            lines.size,
            cells.keys.filter { cells[it] == 'S' }.toVavrStream().toSet(),
            cells.keys.filter { cells[it] == 'E' }.toVavrStream().toSet(),
        )
    }
}