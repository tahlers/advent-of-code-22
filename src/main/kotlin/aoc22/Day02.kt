package aoc22

import io.vavr.kotlin.toVavrList
import io.vavr.kotlin.tuple
import java.lang.IllegalArgumentException

enum class RPCGame(private val baseValue: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    fun play(other: RPCGame): Int {
        val gameValue = when {
            this == ROCK && other == ROCK -> 3
            this == ROCK && other == PAPER -> 0
            this == ROCK && other == SCISSORS -> 6
            this == PAPER && other == ROCK -> 6
            this == PAPER && other == PAPER -> 3
            this == PAPER && other == SCISSORS -> 0
            this == SCISSORS && other == ROCK -> 0
            this == SCISSORS && other == PAPER -> 6
            this == SCISSORS && other == SCISSORS -> 3
            else -> throw IllegalStateException()
        }
        return gameValue + baseValue
    }

    fun findComplement(other: Char): RPCGame {
        return when {
            this == ROCK && other == 'X' -> SCISSORS
            this == ROCK && other == 'Y' -> ROCK
            this == ROCK && other == 'Z' -> PAPER
            this == PAPER && other == 'X' -> ROCK
            this == PAPER && other == 'Y' -> PAPER
            this == PAPER && other == 'Z' -> SCISSORS
            this == SCISSORS && other == 'X' -> PAPER
            this == SCISSORS && other == 'Y' -> SCISSORS
            this == SCISSORS && other == 'Z' -> ROCK
            else -> throw IllegalStateException()
        }
    }

    companion object {
        fun parse(char: Char): RPCGame {
            return when (char) {
                'A', 'X' -> ROCK
                'B', 'Y' -> PAPER
                'C', 'Z' -> SCISSORS
                else -> throw IllegalArgumentException()
            }
        }
    }
}

object Day02 {

    fun calculateScore(input: String): Int {
        val lines = input.trimEnd().lines().toVavrList()
        val parsed = lines.map { line ->
            val (elfChoice, myChoice) = line.split(" ")
            tuple(RPCGame.parse(elfChoice.first()), RPCGame.parse(myChoice.first()))
        }
        return parsed.map { it._2.play(it._1) }.sum().toInt()
    }

    fun calculateScoreComplement(input: String): Int {
        val lines = input.trimEnd().lines().toVavrList()
        val parsed = lines.map { line ->
            val (elfChoice, myChoice) = line.split(" ")
            val elfGame = RPCGame.parse(elfChoice.first())
            val myGame = elfGame.findComplement(myChoice.first())
            tuple(elfGame, myGame)
        }
        return parsed.map { it._2.play(it._1) }.sum().toInt()
    }
}