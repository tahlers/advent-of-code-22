package aoc22

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day02Test : FunSpec({

    val sample = """
        A Y
        B X
        C Z
    """.trimIndent()

    test("calculate score") {
        val result = Day02.calculateScore(sample)
        result shouldBe 15
    }

    test("calculate solution one") {
        val input = this.javaClass.getResource("/day02.txt")!!.readText()
        val result = Day02.calculateScore(input)
        result shouldBe 9241
    }

    test("calculate score for second solution") {
        val result = Day02.calculateScoreComplement(sample)
        result shouldBe 12
    }

    test("calculate solution two") {
        val input = this.javaClass.getResource("/day02.txt")!!.readText()
        val result = Day02.calculateScoreComplement(input)
        result shouldBe 14610
    }
})
