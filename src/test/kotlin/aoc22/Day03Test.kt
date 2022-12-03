package aoc22

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day03Test : FunSpec({

    val sample = """
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw
    """.trimIndent()

    test("priority should be calclulated correctly") {
        assertSoftly {
            Day03.calcPrio('p') shouldBe 16
            Day03.calcPrio('L') shouldBe 38
            Day03.calcPrio('P') shouldBe 42
            Day03.calcPrio('v') shouldBe 22
            Day03.calcPrio('t') shouldBe 20
            Day03.calcPrio('s') shouldBe 19
        }
    }

    test("find common type priority") {
        val result = Day03.findCommonPrio(sample)
        result shouldBe 157
    }

    test("calculate solution one") {
        val input = this.javaClass.getResource("/day03.txt")!!.readText()
        val result = Day03.findCommonPrio(input)
        result shouldBe 7793
    }

    test("find common badge prio") {
        val result = Day03.findBadgePrio(sample)
        result shouldBe 70
    }

    test("calculate solution two") {
        val input = this.javaClass.getResource("/day03.txt")!!.readText()
        val result = Day03.findBadgePrio(input)
        result shouldBe 2499
    }
})
