package aoc22

object Day06 {

    fun calcStartOfDistinctChars(input: String, size: Int = 4): Int {
        val windowed = input.trim().windowedSequence(size).withIndex()
        val packetStart = windowed.first { it.value.toSet().size == size }
        return packetStart.index + size
    }
}