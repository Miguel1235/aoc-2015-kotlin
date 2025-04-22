fun main() {
    fun part1(medicineMolecule: String, replacements: List<String>): Int {
        val mapReplacements = mutableMapOf<String, MutableList<String>>()
        for (replacement in replacements) {
            val (from, to) = replacement.split(" => ")
            mapReplacements.putIfAbsent(from, mutableListOf())
            mapReplacements[from]!!.add(to)
        }


        val result = buildSet {
            for (i in medicineMolecule.indices) {
                val currentChar = medicineMolecule[i]
                val currentNext2 = "${medicineMolecule[i]}${medicineMolecule.getOrElse(i+1) { 'X' }}"

                val replacementsForChar = mapReplacements[currentChar.toString()]
                val replacementForNext2 = mapReplacements[currentNext2]

                val replacement = when {
                    replacementForNext2 != null -> replacementForNext2
                    replacementsForChar != null -> replacementsForChar
                    else -> null
                }

                if(replacement.isNullOrEmpty()) {
                    continue
                }

                for (replacement in replacement) {
                    add(medicineMolecule.replaceRange(i,if(replacementForNext2 != null) i+2 else i+1, replacement))
                }
            }
        }
        return result.size
    }

    val testInput = readInput("Day19_test")
    part1(testInput.last(), testInput.dropLast(2)).println()
    check(part1(testInput.last(), testInput.dropLast(2)) == 4)

    val input = readInput("Day19")
    check(part1(input.last(), input.dropLast(2)) == 535)
}