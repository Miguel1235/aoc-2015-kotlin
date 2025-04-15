fun main() {
    data class Racer(
        val name: String,
        val speed: Int,
        val flyingTime: Int,
        val restingTime: Int,
        var currentDistance: Int = 0,
        var currentTime: Int = 0,
        var points: Int = 0
    ) {
        fun move() {
            val toMod = flyingTime + restingTime
            currentDistance += if (currentTime % toMod < flyingTime) speed else 0
            currentTime++
        }
    }

    fun obtainRacers(input: List<String>): List<Racer> {
        val regex = Regex("""^(\w+) can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds\.""")
        return buildList {
            for (line in input) {
                val (_, name, v, t, r) = regex.find(line)!!.groupValues
                add(Racer(name, v.toInt(), t.toInt(), r.toInt()))
            }
        }
    }

    fun part1(input: List<String>, seconds: Int = 1000): Int {
        val racers = obtainRacers(input)
        repeat(seconds) {
            racers.forEach { it.move() }
        }
        return racers.maxOf { it.currentDistance }
    }

    fun part2(input: List<String>, seconds: Int = 1000): Int {
        val racers = obtainRacers(input)
        repeat(seconds) {
            racers.forEach { it.move() }
            val maxDistance = racers.maxOf { it.currentDistance }
            racers.filter { it.currentDistance == maxDistance }.forEach { it.points++ }
        }
        return racers.maxOf { it.points }
    }

    val testInput = readInput("Day14_test")
    check(part1(testInput) == 1120)
    check(part2(testInput, 1000) == 689)

    val input = readInput("Day14")
    check(part1(input, 2503) == 2696)
    check(part2(input, 2503) == 1084)
}