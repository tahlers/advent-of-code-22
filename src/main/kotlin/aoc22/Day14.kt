package aoc22

import kotlin.math.min
import kotlin.math.max


import kotlin.math.abs

object Day14 {

    enum class Tile(val symbol: Char) {
        BLOCK('▓'), RESTING('o'),
    }

    data class Pos(val x: Int, val y: Int) {
        fun positionsBetween(other: Pos): Set<Pos> {
            val xRange = min(x, other.x)..max(x, other.x)
            val yRange = min(y, other.y)..max(y, other.y)
            return xRange.flatMap { x ->
                yRange.map { y ->
                    Pos(x, y)
                }
            }.toSet()
        }
    }

    data class World(val grid: Map<Pos, Tile>, val voidLevel: Int, val start: Pos, val currentSand: Pos = start) {

        fun nextState(): World {
            val nextSand = nextFallDownPos(currentSand)
            return if (nextSand == null) {
                this.copy(grid = grid + (currentSand to Tile.RESTING), currentSand = start)
            } else {
                this.copy(currentSand = nextSand)
            }
        }

        private fun nextFallDownPos(sand: Pos): Pos? =
            listOf(sand.x, sand.x - 1, sand.x + 1).map { Pos(it, sand.y + 1) }.firstOrNull { it !in grid }
    }

    fun calcSandTilesAfterRest(input: String): Int {
        val state = parse(input)
        val finalState = simulateSandWithVoid(state)
        //printGrid(state)
        //printGrid(finalState)
        return finalState.grid.values.filter { it == Tile.RESTING }.size
    }

    fun calcFilledWithSand(input: String): Int {
        val state = parse(input)
        val withBottom = addBottom(state)
        val finalState = simulateSandWithBottom(withBottom)
        printGrid(finalState)
        return finalState.grid.values.filter { it == Tile.RESTING }.size
    }

    private fun simulateSandWithBottom(state: World): World {
        val states = generateSequence(state) { current ->
            if (state.start in current.grid) null else current.nextState()
        }
        return states.last()
    }

    private fun addBottom(state: World): World {
        val bottomY = state.grid.keys.maxOf { it.y } + 2
        val bottomTiles =
            (((state.start.x - bottomY))..((state.start.x + bottomY))).map { Pos(it, bottomY) to Tile.BLOCK }
        return state.copy(grid = state.grid + bottomTiles)
    }

    private fun simulateSandWithVoid(state: World): World {
        val states = generateSequence(state) { current ->
            if (current.currentSand.y > state.voidLevel) null else current.nextState()
        }
        return states.last()
    }

    private fun parse(input: String): World {
        val lines = input.trimEnd().lines()
        val blockPositions = lines.flatMap { line ->
                val posStrings = line.split(" -> ")
                val positions = posStrings.map { posString ->
                    val xPos = posString.substringBefore(",").toInt()
                    val yPos = posString.substringAfter(",").toInt()
                    Pos(xPos, yPos)
                }
                linesToTiles(positions)
            }.toMap()
        val voidLevel = (blockPositions.keys.maxOfOrNull { it.y } ?: 0) + 1
        return World(blockPositions, voidLevel, Pos(500, 0))
    }

    private fun linesToTiles(positions: List<Pos>): Set<Pair<Pos, Tile>> {
        val blockPositions = positions.zipWithNext().flatMap { it.first.positionsBetween(it.second) }
        return blockPositions.map { it to Tile.BLOCK }.toSet()
    }

    private fun printGrid(state: World) {
        val minX = abs(state.grid.keys.minOf { it.x })
        val maxX = abs(state.grid.keys.maxOf { it.x })
        val minY = abs(state.grid.keys.minOf { it.y })
        val maxY = abs(state.grid.keys.maxOf { it.y })

        (minY..maxY).forEach { y ->
            val line = (minX..maxX).map { x ->
                if (Pos(x, y) !in state.grid) '.' else state.grid.getValue(Pos(x, y)).symbol
            }.joinToString("")
            println(line)
        }
        println()
    }
}