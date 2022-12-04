package aoc22

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day04Test : FunSpec({

    val sample = """
        2-4,6-8
        2-3,4-5
        5-7,7-9
        2-8,3-7
        6-6,4-6
        2-6,4-8
    """.trimIndent()

    test("calculate fully contains") {
        val result = Day04.fullyContained(sample)
        result shouldBe 2
    }

    test("calculate solution one") {
        val input = this.javaClass.getResource("/day04.txt")!!.readText()
        val result = Day04.fullyContained(input)
        result shouldBe 494
    }

    test("calculate overlaps") {
        val result = Day04.overlapCount(sample)
        result shouldBe 4
    }

    test("calculate solution two") {
        val input = this.javaClass.getResource("/day04.txt")!!.readText()
        val result = Day04.overlapCount(input)
        result shouldBe 833
    }
})
