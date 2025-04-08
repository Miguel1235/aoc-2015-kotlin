fun main() {
    fun obtainNewString(input: String): String {
        val inputList = input.toList().map { it.toString() }
        var current = inputList.first()
        var counter = 0

        return buildString {
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
            result = obtainNewString(result)
        }
        return result.length
    }

    val testInput = readInput("Day10_test").first()
    check(part1(testInput) == 237746)

    val input = readInput("Day10").first()
    check(part1(input) == 252594)
    check(part1(input, 50) == 3579328)
}