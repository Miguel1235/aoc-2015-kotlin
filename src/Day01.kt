fun main() {
    val part1 = { input: List<Char> -> input.fold(0) { acc, c -> acc + if (c == '(') 1 else -1 } }
    val part2 = { input: List<Char> ->
        input.runningFold(0) { acc, c -> acc + if (c == '(') 1 else -1 }.indexOfFirst { it == -1 }
    }

    val testInput = readOneLineInput("Day01_test")
    check(part1(testInput) == 3)

    val input = readOneLineInput("Day01")
    check(part1(input) == 74)
    check(part2(input) == 1795)
}
