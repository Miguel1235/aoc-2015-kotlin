enum class Action {
    TURN_ON,
    TURN_OFF,
    TOGGLE
}

data class Instruction(val action: Action, val col: Pair<Int, Int>, val row: Pair<Int, Int>)

fun main() {
    fun parseInput(instructions: List<String>): List<Instruction> {
        return buildList {
            for (instruction in instructions) {
                val regex = Regex("""\d+""")
                val (colS, rowS, colE, rowE) = regex.findAll(instruction).map { it.value.toInt() }.toList()

                val action = when {
                    instruction.contains("on") -> Action.TURN_ON
                    instruction.contains("off") -> Action.TURN_OFF
                    else -> Action.TOGGLE
                }

                add(Instruction(action, Pair(colS, colE), Pair(rowS, rowE)))
            }
        }
    }

    fun prettyPrint(grid: List<List<Int>>) {
        println(grid)
        for (row in grid.indices) {
            val cols = grid[row].indices
            for (col in cols) {
                print(grid[row][col])
            }
            println()
        }
    }

    fun updateGrid(grid: List<MutableList<Int>>, instruction: Instruction, isPart2: Boolean = false): List<MutableList<Int>> {
        val (rs, re) = instruction.row
        val (cs, ce) = instruction.col

        for (r in rs until re + 1) {
            for (c in cs until ce + 1) {
                when (instruction.action) {
                    Action.TURN_ON -> {
                        if(isPart2) {
                            grid[r][c] += 1
                        } else {
                            grid[r][c] = 1
                        }
                    }
                    Action.TURN_OFF -> {
                        if(isPart2 && grid[r][c] > 0) {
                            grid[r][c] -= 1
                        } else {
                            grid[r][c] = 0
                        }
                    }
                    Action.TOGGLE -> {
                        if(isPart2) {
                            grid[r][c] += 2
                        } else {
                            grid[r][c] = if (grid[r][c] == 1) 0 else 1
                        }
                    }
                }
            }
        }
        return grid
    }

    fun part(instructions: List<Instruction>, isPart2: Boolean = false): Int {
        var grid = List(1000) {
            MutableList(1000) {
                0
            }
        }

        for (instruction in instructions) {
            grid = updateGrid(grid, instruction, isPart2)
        }

        return grid.flatten().sumOf { it }
    }

//    val testInput = parseInput(readInput("Day06_test"))
//    part1(testInput)

    val input = parseInput(readInput("Day06"))
    check(part(input) == 377891)
    part(input, true).println()

}