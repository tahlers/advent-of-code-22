package aoc22

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day06Test : FunSpec({

    test("calculate start-of-packet") {
        assertSoftly {
            Day06.calcStartOfDistinctChars("mjqjpqmgbljsphdztnvjfqwrcgsmlb") shouldBe 7
            Day06.calcStartOfDistinctChars("bvwbjplbgvbhsrlpgdmjqwftvncz") shouldBe 5
            Day06.calcStartOfDistinctChars("nppdvjthqldpwncqszvftbrmjlhg") shouldBe 6
            Day06.calcStartOfDistinctChars("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") shouldBe 10
            Day06.calcStartOfDistinctChars("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") shouldBe 11
        }
    }

    test("calculate solution one") {
        val input = this.javaClass.getResource("/day06.txt")!!.readText()
        val result = Day06.calcStartOfDistinctChars(input)
        result shouldBe 1142
    }

    test("calculate start-of-message") {
        assertSoftly {
            Day06.calcStartOfDistinctChars("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 14) shouldBe 19
            Day06.calcStartOfDistinctChars("bvwbjplbgvbhsrlpgdmjqwftvncz", 14) shouldBe 23
            Day06.calcStartOfDistinctChars("nppdvjthqldpwncqszvftbrmjlhg", 14) shouldBe 23
            Day06.calcStartOfDistinctChars("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 14) shouldBe 29
            Day06.calcStartOfDistinctChars("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 14) shouldBe 26
        }
    }

    test("calculate solution two") {
        val input = this.javaClass.getResource("/day06.txt")!!.readText()
        val result = Day06.calcStartOfDistinctChars(input, 14)
        result shouldBe 1142
    }
})
