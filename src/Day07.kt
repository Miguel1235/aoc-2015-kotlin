fun getWireValue(wire: String, results: Map<String, Int>): Int? {
    return runCatching {
        wire.toInt()
    }.getOrElse {
        results[wire]
    }
}

fun part1(instructions: List<String>): Int {
    val results = mutableMapOf<String, Int>()

    repeat(1000) {
        val assignmentsRegex = Regex("""^(\d+|\w+) -> (\d+|\w+)$""")
        for (instruction in instructions) {
            val operation = assignmentsRegex.find(instruction)?.groupValues?.takeLast(2) ?: continue

            val (value, signal) = operation
                val w1Value = getWireValue(value, results) ?: continue
                results[signal] = w1Value
            }

        val gatesRegex = Regex("""^(\d+|\w+) (AND|OR|LSHIFT|RSHIFT) (\d+|\w+) -> (\d+|\w+)${'$'}$""")
        for (instruction in instructions) {
            val operation = gatesRegex.find(instruction)?.groupValues?.takeLast(4)
            if (operation.isNullOrEmpty()) continue
            val (w1, op, w2, r) = operation
            val w1Value = getWireValue(w1, results)
            val w2Value = getWireValue(w2, results)
            if (w1Value == null || w2Value == null) {
                continue
            }
            results[r] = when (op) {
                "AND" -> w1Value and w2Value
                "OR" -> w1Value or w2Value
                "LSHIFT" -> w1Value shl w2Value
                else -> w1Value shr w2Value
            }
        }

        val notRegex = Regex("""^NOT (\d+|\w+) -> (\d+|\w+)$""")
        for(instruction in instructions) {
            val notOps = notRegex.find(instruction)?.groupValues?.takeLast(2)
            if (notOps.isNullOrEmpty()) continue
            val (w1, r) = notOps
            val w1Value = getWireValue(w1, results) ?: continue
            results[r] = w1Value.inv() + 65536
        }
    }
    return results["a"] ?: 0
}

fun main() {
//    val testInput = readInput("Day07_test")
//    part1(testInput)

    val input = readInput("Day07")
    check(part1(input) == 46065)

    val  assignRegex = Regex("""^(\d+) -> b$""")
    val newInput = input.map {
        val findLine  = assignRegex.find(it)?.groupValues
        if (findLine == null) {
            it
        } else {
            "${part1(input)} -> b"
        }
    }

    check(part1(newInput) == 14134)
}