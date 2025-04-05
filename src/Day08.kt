fun String.obtainRealLen(): Int {
    return this.replace("""\"""", "*")
        .replace("""\\""", "*")
        .replace(Regex("""\\x[a-f0-9]{2}"""), "_")
        .length - 2
}

fun part1(input: List<String>): Int {
    return input.fold(0) { acc, l -> acc + (l.length - l.obtainRealLen()) }
}
fun main() {
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 15)

    val input = readInput("Day08")
    check(part1(input) == 1350)
}