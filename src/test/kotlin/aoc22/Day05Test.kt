package aoc22

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day05Test : FunSpec({

    val sample = """
    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
    """.trimIndent()

    test("calculate top crates") {
        val result = Day05.calculateTopCrates(sample)
        result shouldBe "CMZ"
    }

    test("calculate solution one") {
        val input = this.javaClass.getResource("/day05.txt")!!.readText()
        val result = Day05.calculateTopCrates(input)
        result shouldBe "WCZTHTMPS"
    }

    test("calculate top crates with CrateMover 9001") {
        val result = Day05.calculateTopCrates(sample, false)
        result shouldBe "MCD"
    }

    test("calculate solution two") {
        val input = this.javaClass.getResource("/day05.txt")!!.readText()
        val result = Day05.calculateTopCrates(input, false)
        result shouldBe "BLSGJSDTS"
    }
})
