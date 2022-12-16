package aoc22

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day13Test : FunSpec({

    val sample = """
        [1,1,3,1,1]
        [1,1,5,1,1]

        [[1],[2,3,4]]
        [[1],4]

        [9]
        [[8,7,6]]

        [[4,4],4,4]
        [[4,4],4,4,4]

        [7,7,7,7]
        [7,7,7]

        []
        [3]

        [[[]]]
        [[]]

        [1,[2,[3,[4,[5,6,7]]]],8,9]
        [1,[2,[3,[4,[5,6,0]]]],8,9]
    """.trimIndent()

    test("calculate sum of right ordered pairs") {
        val result = Day13.calcSumOfRightOrderPairs(sample)
        result shouldBe 13
    }

    test("calculate solution one") {
        val input = this.javaClass.getResource("/day13.txt")!!.readText()
        val result = Day13.calcSumOfRightOrderPairs(input)
        result shouldBe 5682
    }

    test("calculate decoder key") {
        val result = Day13.calcDecoderKey(sample)
        result shouldBe 140
    }

    test("calculate solution two") {
        val input = this.javaClass.getResource("/day13.txt")!!.readText()
        val result = Day13.calcDecoderKey(input)
        result shouldBe 20304
    }
})
