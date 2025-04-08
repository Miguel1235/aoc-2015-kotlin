fun main() {
    fun generateNextLookAndSayTerm(input: String): String {
        val inputList = input.toList().map { it.toString() }

        return buildString {
            var current = inputList.first()
            var counter = 0
            for(char in inputList) {
                if(current == char) {
                    counter++
                    continue
                }
                append(counter.toString())
                append(current)
                current = char
                counter = 1
            }
            append(counter.toString())
            append(current)
        }
    }

    fun part1(input: String, times: Int = 40): Int {
        var result = input
        repeat(times) {
            result = generateNextLookAndSayTerm(result)
        }
        return result.length
    }

    val testInput = readInput("Day10_test").first()
    check(part1(testInput) == 237746)

    val input = readInput("Day10").first()
    check(part1(input) == 252594)
    check(part1(input, 50) == 3579328)
}