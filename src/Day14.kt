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

    fun part(input: List<String>, seconds: Int = 1000, isPart2: Boolean = false): Int {
        val racers = obtainRacers(input)
        repeat(seconds) {
            racers.forEach { it.move() }
            if(isPart2) {
                val maxDistance = racers.maxOf { it.currentDistance }
                racers.filter { it.currentDistance == maxDistance }.forEach { it.points++ }
            }
        }
        return racers.maxOf { if(isPart2) it.points else it.currentDistance }
    }

    val testInput = readInput("Day14_test")
    check(part(testInput) == 1120)
    check(part(testInput, 1000, true) == 689)

    val input = readInput("Day14")
    check(part(input, 2503) == 2696)
    check(part(input, 2503, true) == 1084)
}