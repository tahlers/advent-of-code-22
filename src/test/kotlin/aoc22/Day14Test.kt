package aoc22

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day14Test : FunSpec({

    val sample = """
        498,4 -> 498,6 -> 496,6
        503,4 -> 502,4 -> 502,9 -> 494,9
    """.trimIndent()

    test("calculate sand tiles after rest") {
        val result = Day14.calcSandTilesAfterRest(sample)
        result shouldBe 24
    }

    test("calculate solution one") {
        val input = this.javaClass.getResource("/day14.txt")!!.readText()
        val result = Day14.calcSandTilesAfterRest(input)
        result shouldBe 614
    }

    test("calculate sand filling") {
        val result = Day14.calcFilledWithSand(sample)
        result shouldBe 93
    }

    test("calculate solution two") {
        val input = this.javaClass.getResource("/day14.txt")!!.readText()
        val result = Day14.calcFilledWithSand(input)
        result shouldBe 26170
    }
})
