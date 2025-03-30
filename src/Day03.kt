data class House(val r: Int = 0, val c: Int = 0)

fun main() {
    fun housesVisited(directions: List<Char>): Set<House> {
        var currentHouse = House()
        val houses = mutableSetOf(currentHouse)
        for (direction in directions) {
            when (direction) {
                '>' -> currentHouse = House(currentHouse.r, currentHouse.c + 1)
                '<' -> currentHouse = House(currentHouse.r, currentHouse.c - 1)
                '^' -> currentHouse = House(currentHouse.r - 1, currentHouse.c)
                'v' -> currentHouse = House(currentHouse.r + 1, currentHouse.c)
            }
            houses.add(currentHouse)
        }
        return houses
    }

    fun part1(directions: List<Char>): Int {
        return housesVisited(directions).size
    }


    fun filterInput(input: List<Char>, odd: Boolean = true): List<Char> {
        return input.filterIndexed { i, _ -> i % 2 == if (odd) 0 else 1 }
    }

    fun part2(directions: List<Char>): Int {
        val s = housesVisited(filterInput(directions, false)) + housesVisited(filterInput(directions))
        return s.size
    }

    val testInput = readOneLineInput("Day03_test")
    part1(testInput)
    part2(testInput)

    val input = readOneLineInput("Day03")
    part1(input).println()
    part2(input).println()
}