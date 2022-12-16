package aoc22

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day11Test : FunSpec({

    val sample = """
    Monkey 0:
      Starting items: 79, 98
      Operation: new = old * 19
      Test: divisible by 23
        If true: throw to monkey 2
        If false: throw to monkey 3

    Monkey 1:
      Starting items: 54, 65, 75, 74
      Operation: new = old + 6
      Test: divisible by 19
        If true: throw to monkey 2
        If false: throw to monkey 0
    
    Monkey 2:
      Starting items: 79, 60, 97
      Operation: new = old * old
      Test: divisible by 13
        If true: throw to monkey 1
        If false: throw to monkey 3
    
    Monkey 3:
      Starting items: 74
      Operation: new = old + 3
      Test: divisible by 17
        If true: throw to monkey 0
        If false: throw to monkey 1
    """.trimIndent()

    test("calculate monkey business level after 20 rounds") {
        val result = Day11.calculateMonkeyBusinessLevel(sample, 20)
        result shouldBe 10605
    }

    test("calculate solution one") {
        val input = this.javaClass.getResource("/day11.txt")!!.readText()
        val result = Day11.calculateMonkeyBusinessLevel(input, 20)
        result shouldBe 112815
    }

    test("calculate monkey business level after 10000 rounds") {
        val result = Day11.calculateMonkeyBusinessLevel(sample, 10000, false)
        result shouldBe 2713310158L
    }

    test("calculate solution two") {
        val input = this.javaClass.getResource("/day11.txt")!!.readText()
        val result = Day11.calculateMonkeyBusinessLevel(input, 10000, false)
        result shouldBe 25738411485L
    }
})
