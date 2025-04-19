fun main() {

    fun obtainAllCombinations(input: List<Int>): List<List<Int>> {
        return buildList {
            for(i in 1..input.size) {
                add(combinations(input, i))
            }
        }.flatten()
    }

    fun part1(input: List<Int>, liters2Fill: Int): Int {
        return obtainAllCombinations(input).count { it.sum() == liters2Fill }
    }

    fun part2(input: List<Int>, liters2Fill: Int): Int {
        val combinations = obtainAllCombinations(input)

        val minContainers = combinations.find { it.sum() == liters2Fill }!!.size
        return combinations.count { it.size == minContainers && it.sum() == liters2Fill }
    }

    val testInput = readInput("Day17_test").map { it.toInt() }
    check(part1(testInput, 25) == 4)
    check(part2(testInput, 25) == 3)

    val input = readInput("Day17").map { it.toInt() }
    check(part1(input, 150) == 1638)
    check(part2(input, 150) == 17)
}