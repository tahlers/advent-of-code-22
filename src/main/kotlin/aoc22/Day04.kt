package aoc22

object Day04 {
    fun fullyContained(input: String): Int {
        val lines = input.trimEnd().lines()
        val sets = toSets(lines)
        val filtered = sets.filter { it.first.containsAll(it.second) || it.second.containsAll(it.first) }
        return filtered.size
    }

    fun overlapCount(input: String): Int {
        val lines = input.trimEnd().lines()
        val sets = toSets(lines)
        val filtered = sets.filter { it.first.intersect(it.second).isNotEmpty() }
        return filtered.size
    }

    private fun toSets(lines: List<String>) = lines.map { line ->
        val (first, second) = line.split(',')
            .map { rangeLine ->
                val (start, end) = rangeLine.split('-')
                start.toInt()..end.toInt()
            }
        first.toSet() to second.toSet()
    }
}