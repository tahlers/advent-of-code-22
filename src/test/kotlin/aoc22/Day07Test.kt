package aoc22

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day07Test : FunSpec({

    val sample = """
        ${'$'} cd /
        ${'$'} ls
        dir a
        14848514 b.txt
        8504156 c.dat
        dir d
        ${'$'} cd a
        ${'$'} ls
        dir e
        29116 f
        2557 g
        62596 h.lst
        ${'$'} cd e
        ${'$'} ls
        584 i
        ${'$'} cd ..
        ${'$'} cd ..
        ${'$'} cd d
        ${'$'} ls
        4060174 j
        8033020 d.log
        5626152 d.ext
        7214296 k
    """.trimIndent()

    test("calculate sum of dir size below 100000") {
        val result = Day07.calcDirSizeUpTo(sample)
        result shouldBe 95437
    }

    test("calculate solution one") {
        val input = this.javaClass.getResource("/day07.txt")!!.readText()
        val result = Day07.calcDirSizeUpTo(input)
        result shouldBe 1513699
    }

    test("calculate dir to be deleted size") {
        val result = Day07.calcDirSizeOfSmallest(sample)
        result shouldBe 24933642
    }

    test("calculate solution two") {
        val input = this.javaClass.getResource("/day07.txt")!!.readText()
        val result = Day07.calcDirSizeOfSmallest(input)
        result shouldBe 7991939
    }
})
