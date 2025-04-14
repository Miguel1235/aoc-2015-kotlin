fun main() {
    fun permutations(names: List<String>): List<List<String>> {
        if (names.isEmpty()) return listOf(emptyList())
        val result = mutableListOf<List<String>>()

        for(i in names.indices) {
            val name = names[i]
            val remaining = names.toMutableList().apply { removeAt(i) }

            permutations(remaining).forEach {
                result.add(listOf(name) + it)
            }
        }
        return result
    }

    fun obtainHappiness(lines: List<String>): Map<String, Int> {
        val weightRegex = Regex("""(\w+) would (gain|lose) (\d+) happiness units by sitting next to (\w+)""")
        return buildMap {
            for(line in lines) {
                val (_,fn, signal ,weight, sn) = weightRegex.find(line)!!.groupValues
                put("$fn-$sn", if(signal == "gain") weight.toInt() else -weight.toInt())
            }
        }
    }

    fun fixedPermutations(arrangements: List<List<String>>):List<List<Pair<String, String>>> {
        val fixedArrangement = mutableListOf<List<Pair<String, String>>>()

        for(arrangement in arrangements) {
            val nextEachOther = mutableListOf<Pair<String, String>>()

            val list = listOf(arrangement.last()) + arrangement + listOf(arrangement.first())

            for (i in 1 until list.size-1) {
                val name = list[i]
                val prev = list[i-1]
                val next = list[i+1]
                nextEachOther.add(name to prev)
                nextEachOther.add(name to next)
            }
            fixedArrangement.add(nextEachOther)
        }
        return fixedArrangement
    }

    fun part(hp: Map<String, Int>, names: List<String>): Int {
        return fixedPermutations(permutations(names)).maxOf { arr ->  arr.sumOf { hp.getOrDefault("${it.first}-${it.second}", 0) } }
    }

    val testInput = readInput("Day13_test")
    val hpTest = obtainHappiness(testInput)
    val namesTest = hpTest.keys.map { it.split("-") }.flatten().toSet().toList()

    check(part(hpTest, namesTest) == 330)

    val input = readInput("Day13")
    val hp = obtainHappiness(input)
    val names = hp.keys.map { it.split("-") }.flatten().toSet().toMutableList()

    check(part(hp, names) == 618)
    names.add("Miguel")
    check(part(hp, names) == 601)

}