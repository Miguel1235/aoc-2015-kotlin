fun main() {
    data class Dimensions(val l: Long, val w: Long, val h: Long) {
        val volume: Long
            get() = l * w * h
        val area: Long
            get() {
                val base = l * w
                val side = w * h
                val front = h * l
                return 2 * base + 2 * side + 2 * front
            }
        val ribbon: Long
            get() {
                val sideP = 2 * w + 2 * h
                val frontP = 2 * l + 2 * h
                val baseP = 2 * w + 2 * l

                return listOf(sideP, frontP, baseP).min()
            }

        val minSide: Long
            get() {
                val base = l * w
                val side = w * h
                val front = h * l

                return listOf(base, side, front).min()
            }
    }

    val part1 = { dimensions: List<Dimensions> -> dimensions.fold(0L) { acc, d -> acc + (d.area + d.minSide) } }
    val part2 = { dimensions: List<Dimensions> -> dimensions.fold(0L) { acc, d -> acc + (d.ribbon + d.volume) } }


    fun parseInput(input: List<String>): List<Dimensions> {
        val dimRegex = Regex("""(\d+)x(\d+)x(\d+)""")

        return buildList {
            for (line in input) {
                val (l, w, h) = dimRegex.find(line)!!.groupValues.takeLast(3).map { it.toLong() }
                add(Dimensions(l, w, h))
            }
        }
    }

    val testInput = parseInput(readInput("Day02_test"))
    part1(testInput)
    check(part1(testInput) == 58L)
    check(part2(testInput) == 34L)

    val input = parseInput(readInput("Day02"))
    check(part1(input) == 1598415L)
    check(part2(input) == 3812909L)
}