fun main() {
    fun obtainNumber(line: String): Int {
        val numsRegex = Regex("""-?\d+""")
        return numsRegex
            .findAll(line)
            .map { it.value.toInt() }
            .sumOf { it }
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { obtainNumber(it) }
    }

    val testInput = readInput("Day12_test")
    check(part1(testInput) == 9)

    val input = readInput("Day12")
    check(part1(input) == 156366)

    for(line in input) {
        println("The line is $line")
    }
}