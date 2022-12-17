package aoc22

import kotlin.math.abs

object Day15 {

    data class Pos(val x: Int, val y: Int) {
        fun distance(other: Pos) = abs(other.x - x) + abs(other.y - y)
    }

    data class Sensor(val pos: Pos, val beacon: Pos) {
        private val beaconDistance = pos.distance(beacon)

        fun inReach(other: Pos): Boolean = pos.distance(other) <= beaconDistance

        fun border(searchSize: Int): Set<Pos> {
            val border = ((-beaconDistance - 1)..(beaconDistance + 1)).withIndex().flatMap { (index, value) ->
                    val pos1 = Pos(pos.x - value, pos.y + index)
                    val pos2 = Pos(pos.x - value, pos.y - index)
                    setOf(pos1, pos2)
                }.filter { it.x in 0..searchSize && it.y in 0..searchSize }
            return border.toSet()
        }
    }

    fun calculateOccupiedForLine(input: String, yPos: Int = 10): Int {
        val sensorSet = parseSensors(input)
        val allPos = sensorSet.flatMap { listOf(it.pos, it.beacon) }
        val minX = allPos.minOf { it.x }
        val width = allPos.maxOf { it.x } - minX
        val occupied = ((minX - width)..(minX + 2 * width)).filter { isOccupied(Pos(it, yPos), sensorSet) }
            .filter { Pos(it, yPos) !in sensorSet.map { s -> s.beacon } }
        return occupied.size
    }

    fun findTuningFrequency(input: String, searchSize: Int): Long {
        val sensorSet = parseSensors(input)
        val allBorderPos = sensorSet.flatMap { it.border(searchSize) }
        val free = allBorderPos.first { !isOccupied(it, sensorSet) }
        return free.x.toLong() * 4000000 + free.y
    }

    private fun isOccupied(pos: Pos, sensors: Set<Sensor>) = sensors.any { it.inReach(pos) }

    private fun parseSensors(input: String): Set<Sensor> {
        val regex = """Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)""".toRegex()
        val lines = input.trimEnd().lines()
        return lines.map { line ->
            val matchResult = regex.matchEntire(line)
            val (sensorX, sensorY, beaconX, beaconY) = matchResult!!.destructured
            Sensor(Pos(sensorX.toInt(), sensorY.toInt()), Pos(beaconX.toInt(), beaconY.toInt()))
        }.toSet()
    }
}