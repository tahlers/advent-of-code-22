package aoc22

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day09Test : FunSpec({

    val sample = """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent()

    val sample2 = """
        R 5
        U 8
        L 8
        D 3
        R 17
        D 10
        L 25
        U 20
    """.trimIndent()

    test("calculate count of places visited by tail") {
        val result = Day09.tailVisitedCounts(sample)
        result shouldBe 13
    }

    test("calculate solution one") {
        val input = this.javaClass.getResource("/day09.txt")!!.readText()
        val result = Day09.tailVisitedCounts(input, 2)
        result shouldBe 5902
    }

    test("simulate long rope") {
        assertSoftly {
            Day09.tailVisitedCounts(sample, 10) shouldBe 1
            Day09.tailVisitedCounts(sample2, 10) shouldBe 36
        }
    }

    test("calculate solution two") {
        val input = this.javaClass.getResource("/day09.txt")!!.readText()
        val result = Day09.tailVisitedCounts(input, 10)
        result shouldBe 2445
    }
})
