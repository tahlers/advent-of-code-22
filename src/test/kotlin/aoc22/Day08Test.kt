package aoc22

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day08Test : FunSpec({

    val sample = """
        30373
        25512
        65332
        33549
        35390
    """.trimIndent()

    test("calculate visible tree count") {
        val result = Day08.countVisibleTrees(sample)
        result shouldBe 21
    }

    test("calculate solution one") {
        val input = this.javaClass.getResource("/day08.txt")!!.readText()
        val result = Day08.countVisibleTrees(input)
        result shouldBe 1849
    }

    test("calculate scenic score") {
        val result = Day08.calculateScenicScore(sample)
        result shouldBe 8
    }

    test("calculate solution two") {
        val input = this.javaClass.getResource("/day08.txt")!!.readText()
        val result = Day08.calculateScenicScore(input)
        result shouldBe 201600
    }
})
