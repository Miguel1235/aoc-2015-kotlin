fun main() {
    fun updateGrid(starter: List<List<Char>>, rowI: Int, colI: Int, isPart2: Boolean = false): Char {
        val grid = starter.map { it.toMutableList() }
        val nbs = listOf(
            grid.getOrNull(rowI - 1)?.getOrNull(colI) ?: '.',
            grid.getOrNull(rowI - 1)?.getOrNull(colI - 1) ?: '.',
            grid.getOrNull(rowI - 1)?.getOrNull(colI + 1) ?: '.',
            grid.getOrNull(rowI)?.getOrNull(colI - 1) ?: '.',
            grid.getOrNull(rowI)?.getOrNull(colI + 1) ?: '.',
            grid.getOrNull(rowI + 1)?.getOrNull(colI - 1) ?: '.',
            grid.getOrNull(rowI + 1)?.getOrNull(colI + 1) ?: '.',
            grid.getOrNull(rowI + 1)?.getOrNull(colI) ?: '.'
        ).count { it == '#' }

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


    fun makeGrid(input: List<String>, isPart2: Boolean = false): MutableList<MutableList<Char>> {
        return input.map { it.toMutableList() }.toMutableList()
    }

    fun part1(input: List<String>, steps: Int = 4, isPart2: Boolean = false): Int {
        var grid = makeGrid(input, isPart2)

        if (isPart2) {
            grid[0][0] = '#'
            grid[0][grid[0].lastIndex] = '#'
            grid[grid.lastIndex][0] = '#'
            grid[grid.lastIndex][grid[0].lastIndex] = '#'
        }

        repeat(steps) {
            val newGrid = mutableListOf<MutableList<Char>>()

            for (rowI in grid.indices) {
                val row = grid[rowI]
                val newRow = mutableListOf<Char>()
                for (colI in row.indices) {
                    val r = updateGrid(grid, rowI, colI, isPart2)
                    newRow.add(r)
                }
                newGrid.add(newRow)
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