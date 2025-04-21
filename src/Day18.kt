fun main() {
    fun calculateNextState(starter: List<List<Char>>, rowI: Int, colI: Int, isPart2: Boolean = false): Char {
        val grid = starter.map { it.toMutableList() }
        val directions = listOf(
            -1 to 0,  // North
            -1 to -1, // Northwest
            -1 to 1,  // Northeast
            0 to -1,  // West
            0 to 1,   // East
            1 to -1,  // Southwest
            1 to 1,   // Southeast
            1 to 0    // South
        )
        val nbs = directions.count {(r,c) ->
            val nr = rowI + r
            val nc = colI + c
            grid.getOrNull(nr)?.getOrNull(nc) == '#'
        }
        val current = grid[rowI][colI]
        return when {
            isPart2 && ((rowI == 0 && colI == 0) || (rowI == 0 && colI == grid[0].lastIndex) || (rowI == grid.lastIndex && colI == 0) || (rowI == grid.lastIndex && colI == grid[0].lastIndex)) -> {
                '#'
            }
            current == '.' && nbs == 3 -> {
                '#'
            }
            current == '#' && !listOf(2, 3).contains(nbs) -> {
                '.'
            }
            else -> current
        }
    }

    fun makeGrid(input: List<String>, isPart2: Boolean = false): List<List<Char>> {
        val grid =  input.map { it.toMutableList() }.toMutableList()
        if (isPart2) {
            grid[0][0] = '#'
            grid[0][grid[0].lastIndex] = '#'
            grid[grid.lastIndex][0] = '#'
            grid[grid.lastIndex][grid[0].lastIndex] = '#'
        }
        return grid.map { it.toList() }.toList()
    }

    fun part1(input: List<String>, steps: Int = 4, isPart2: Boolean = false): Int {
        var grid = makeGrid(input, isPart2)
        repeat(steps) {
            val newGrid = buildList {
                for (rowI in grid.indices) {
                    val newRow = buildList {
                        for (colI in grid[rowI].indices) {
                            add(calculateNextState(grid, rowI, colI, isPart2))
                        }
                    }
                    add(newRow)
                }
            }
            grid = newGrid
        }
        return grid.sumOf { row -> row.count { it == '#' } }
    }

    val testInput = readInput("Day18_test")
    check(part1(testInput, 5, true) == 17)
    check(part1(testInput) == 4)

    val input = readInput("Day18")
    check(part1(input, 100) == 768)
    check(part1(input, 100, true) == 781)
}