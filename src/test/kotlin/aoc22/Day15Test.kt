package aoc22

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day15Test : FunSpec({

    val sample = """
        Sensor at x=2, y=18: closest beacon is at x=-2, y=15
        Sensor at x=9, y=16: closest beacon is at x=10, y=16
        Sensor at x=13, y=2: closest beacon is at x=15, y=3
        Sensor at x=12, y=14: closest beacon is at x=10, y=16
        Sensor at x=10, y=20: closest beacon is at x=10, y=16
        Sensor at x=14, y=17: closest beacon is at x=10, y=16
        Sensor at x=8, y=7: closest beacon is at x=2, y=10
        Sensor at x=2, y=0: closest beacon is at x=2, y=10
        Sensor at x=0, y=11: closest beacon is at x=2, y=10
        Sensor at x=20, y=14: closest beacon is at x=25, y=17
        Sensor at x=17, y=20: closest beacon is at x=21, y=22
        Sensor at x=16, y=7: closest beacon is at x=15, y=3
        Sensor at x=14, y=3: closest beacon is at x=15, y=3
        Sensor at x=20, y=1: closest beacon is at x=15, y=3
    """.trimIndent()

    test("calculate occupied count for line") {
        val result = Day15.calculateOccupiedForLine(sample)
        result shouldBe 26
    }

    test("calculate solution one") {
        val input = this.javaClass.getResource("/day15.txt")!!.readText()
        val result = Day15.calculateOccupiedForLine(input, 2000000)
        result shouldBe 4951427
    }

    test("calculate tuning frequency") {
        val result = Day15.findTuningFrequency(sample, 20)
        result shouldBe 56000011L
    }

    xtest("calculate solution two") {
        val input = this.javaClass.getResource("/day15.txt")!!.readText()
        val result = Day15.findTuningFrequency(input, 4000000)
        result shouldBe 13029714573243L
    }
})
