package aoc22

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day12Test : FunSpec({

    val sample = """
    Sabqponm
    abcryxxl
    accszExk
    acctuvwj
    abdefghi
    """.trimIndent()

    test("calculate minimum steps to end") {
        val result = Day12.calcMinStepsToEnd(sample)
        result shouldBe 31
    }

    test("calculate solution one") {
        val input = this.javaClass.getResource("/day12.txt")!!.readText()
        val result = Day12.calcMinStepsToEnd(input)
        result shouldBe 339
    }

    test("calculate minimum steps to 'a' level") {
        val result = Day12.calcMinStepsBack(sample)
        result shouldBe 29
    }

    test("calculate solution two") {
        val input = this.javaClass.getResource("/day12.txt")!!.readText()
        val result = Day12.calcMinStepsBack(input)
        result shouldBe 332
    }
})
