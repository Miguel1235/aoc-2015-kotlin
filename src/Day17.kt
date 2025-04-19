fun main() {
    fun part1(input: List<String>, liters2Fill: Int): Int {
        val intInput = input.map { it.toInt() }

        return buildList {
            for (i in 1..intInput.size) {
                add(combinations(intInput, i))
            }
        }.flatten()
            .count {
                it.sum() == liters2Fill
            }
    }

    val testInput = readInput("Day17_test")
    check(part1(testInput, 25) == 4)

    val input = readInput("Day17")
    check(part1(input, 150) == 1638)
}