package aoc22

data class Pos(val x: Int, val y: Int)
typealias Grid = Map<Pos, Int>

object Day08 {
    fun countVisibleTrees(input: String): Int {
        val lines = input.trimEnd().lines()
        val maxX = lines.first().length - 1
        val maxY = lines.size - 1
        val grid = lines.flatMapIndexed { y, line ->
            line.mapIndexed { x, char -> Pos(x, y) to char.digitToInt() }
        }.toMap()
        val visibilities = grid.keys.map { isVisible(it, grid, maxX, maxY) }
        return visibilities.filter { it }.size
    }

    fun calculateScenicScore(input: String): Int {
        val lines = input.trimEnd().lines()
        val maxX = lines.first().length - 1
        val maxY = lines.size - 1
        val grid = lines.flatMapIndexed { y, line ->
            line.mapIndexed { x, char -> Pos(x, y) to char.digitToInt() }
        }.toMap()
        println(calcScenicScore(Pos(2, 3), grid, maxX, maxY))
        val scenicScores = grid.mapValues { calcScenicScore(it.key, grid, maxX, maxY) }
        return scenicScores.values.max()
    }

    private fun isVisible(pos: Pos, grid: Grid, maxX: Int, maxY: Int): Boolean {
        val currentHeight = grid.getOrDefault(pos, 0)
        val leftHeights = (pos.x - 1 downTo 0).map { grid[Pos(it, pos.y)] }.filterNotNull()
        val rightHeights = (pos.x + 1..maxX).map { grid[Pos(it, pos.y)] }.filterNotNull()
        val upHeights = (pos.y - 1 downTo 0).map { grid[Pos(pos.x, it)] }.filterNotNull()
        val downHeights = (pos.y + 1..maxY).map { grid[Pos(pos.x, it)] }.filterNotNull()
        return leftHeights.all { it < currentHeight }
                || rightHeights.all { it < currentHeight }
                || upHeights.all { it < currentHeight }
                || downHeights.all { it < currentHeight }
    }

    private fun calcScenicScore(pos: Pos, grid: Grid, maxX: Int, maxY: Int): Int {
        val currentHeight = grid.getOrDefault(pos, 0)
        val leftHeights = (pos.x - 1 downTo 0).map { grid[Pos(it, pos.y)] }.filterNotNull()
        val rightHeights = (pos.x + 1..maxX).map { grid[Pos(it, pos.y)] }.filterNotNull()
        val upHeights = (pos.y - 1 downTo 0).map { grid[Pos(pos.x, it)] }.filterNotNull()
        val downHeights = (pos.y + 1..maxY).map { grid[Pos(pos.x, it)] }.filterNotNull()
        val lefts = leftHeights.takeWhile { it < currentHeight } to leftHeights.firstOrNull { it >= currentHeight}
        val rights =  rightHeights.takeWhile { it < currentHeight } to rightHeights.firstOrNull { it >= currentHeight}
        val ups = upHeights.takeWhile { it < currentHeight } to upHeights.firstOrNull { it >= currentHeight}
        val downs = downHeights.takeWhile { it < currentHeight } to downHeights.firstOrNull { it >= currentHeight}
        val leftTrees = lefts.first.size + if (lefts.second == null) 0 else 1
        val rightTrees = rights.first.size + if (rights.second == null) 0 else 1
        val upTrees = ups.first.size + if (ups.second == null) 0 else 1
        val downTrees = downs.first.size + if (downs.second == null) 0 else 1

        return leftTrees * rightTrees * upTrees * downTrees
    }
}