package aoc22

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day01Test : FunSpec({

    val sample = """
        1000
        2000
        3000

        4000

        5000
        6000

        7000
        8000
        9000

        10000
    """.trimIndent()

    test("calculate hightest calories test") {
        val result = Day01.calculateHighestCalories(sample)
        result shouldBe 24000
    }

    test("calculate solution one") {
        val input = this.javaClass.getResource("/day01.txt")!!.readText()
        val result = Day01.calculateHighestCalories(input)
        result shouldBe 66719
    }

    test("calculate top three test") {
        val result = Day01.calculateTopThree(sample)
        result shouldBe 45000
    }

    test("calculate solution two") {
        val input = this.javaClass.getResource("/day01.txt")!!.readText()
        val result = Day01.calculateTopThree(input)
        result shouldBe 198551
    }
})
