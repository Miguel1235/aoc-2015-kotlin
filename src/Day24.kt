fun main() {
    fun part1(numbers: List<Int>) {

        val r = buildList {
            for(size in 1..<numbers.size) {
                val combinations = combinations(numbers, size)
                add(combinations)
            }
        }

        // we group to balance
        val groups = r.flatten().groupBy { it.sum() }.filter { it.value.size >= 3}

        for (group in groups) {
            val results = combinations(group.value, 3).filter {
                it.flatten().containsAll(numbers) && it.flatten().toSet().size == it.flatten().size
            }

            val firstE = mutableSetOf<List<Int>>()
            for(result in results) {
                val minSize = result.minOf { it.size }

                val firsts = result.first { it.size == minSize }
                firstE.add(firsts)
            }

            if(firstE.isEmpty()) {
                continue
            }

            val minSizeArr = firstE.minOf { it.size }
            val filtered = firstE.filter { it.size == minSizeArr }
            val minQe = filtered.minOf { it.fold(1) { acc, n -> acc* n } }
            println(minQe)

        }
    }

    val testInput = readInput("Day24_test").map { it.toInt() }
    part1(testInput)

//    val input = readInput("Day24").map { it.toInt() }
//    part1(input)
}