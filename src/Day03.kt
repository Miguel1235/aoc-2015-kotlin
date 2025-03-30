data class House(val r: Int = 0, val c: Int = 0)
enum class Direction {
    LEFT, RIGHT, UP, DOWN
}

fun main() {
    fun housesVisited(directions: List<Direction>): Set<House> {
        var currentHouse = House()
        return buildSet {
            add(currentHouse)
            for (direction in directions) {
                currentHouse = when (direction) {
                    Direction.RIGHT -> currentHouse.copy(c = currentHouse.c + 1)
                    Direction.LEFT -> currentHouse.copy(c = currentHouse.c - 1)
                    Direction.UP -> currentHouse.copy(r = currentHouse.r - 1)
                    Direction.DOWN -> currentHouse.copy(r = currentHouse.r + 1)
                }
                add(currentHouse)
            }
        }
    }

    fun parseInput(input: List<Char>): List<Direction> {
        return buildList {
            for (direction in input) {
                when (direction) {
                    '>' -> add(Direction.RIGHT)
                    '<' -> add(Direction.LEFT)
                    '^' -> add(Direction.UP)
                    'v' -> add(Direction.DOWN)
                }
            }
        }
    }

    val filterInput = { directions: List<Direction>, odd: Boolean -> directions.filterIndexed { i, _ -> i % 2 == if (odd) 0 else 1 } }

    val part1 = { directions: List<Direction> -> housesVisited(directions).size }
    val part2 = { directions: List<Direction> -> (housesVisited(filterInput(directions, false)) + housesVisited(filterInput(directions, true))).size }

    val testInput = parseInput(readOneLineInput("Day03_test"))
    check(part1(testInput) == 2)
    check(part2(testInput) == 11)

    val input = parseInput(readOneLineInput("Day03"))
    check(part1(input) == 2081)
    check(part2(input) == 2341)
}