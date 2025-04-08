fun main() {
    fun obtainNewString(input: String): String {
        val inputList = input.toList().map { it.toString() }

        var current = inputList.first()
        var counter = 0
        val result = mutableListOf<String>()

        for(char in inputList) {
            if(current == char) {
                counter++
                continue
            }
            result.add(counter.toString())
            result.add(current)
            current = char
            counter = 1
        }
        result.add(counter.toString())
        result.add(current)

        return result.joinToString("")
    }

    fun part1(input: String): Int {
        var result = input
        repeat(40) {
            result = obtainNewString(result)
        }
        return result.length
    }

    val testInput = readInput("Day10_test").first()
    check(part1(testInput) == 237746)

    val input = readInput("Day10").first()
    check(part1(input) == 252594)
}