fun main() {
    fun getWireValue(wire: String, results: MutableMap<String, Int>, instructions: Map<String, String>): Int {
        results[wire]?.let { return it }
        wire.toIntOrNull()?.let { return it }

        val instruction = instructions[wire] ?: error("No instruction found for wire: $wire")

        val result = when {
            instruction.matches(Regex("""^(\d+|\w+)$""")) -> {
                getWireValue(instruction, results, instructions)
            }
            instruction.startsWith("NOT ") -> {
                val operand = instruction.substringAfter("NOT ").trim()
                getWireValue(operand, results, instructions).inv() and 0xFFFF
            }
            else -> {
                val parts = instruction.split(" ")
                val left = getWireValue(parts[0], results, instructions)
                val right = getWireValue(parts[2], results, instructions)

                when (parts[1]) {
                    "AND" -> left and right
                    "OR" -> left or right
                    "LSHIFT" -> left shl right and 0xFFFF
                    else -> left ushr right
                }
            }
        }
        results[wire] = result
        return result
    }

    fun part1(instructions: List<String>): Int {
        val wireInstructions = mutableMapOf<String, String>()
        val results = mutableMapOf<String, Int>()

        for (instruction in instructions) {
            val parts = instruction.split(" -> ")
            if (parts.size == 2) {
                val (operation, wire) = parts
                wireInstructions[wire] = operation
            }
        }

        return getWireValue("a", results, wireInstructions)
    }

    fun part2(instructions: List<String>, part1Result: Int): Int {
        val wireInstructions = mutableMapOf<String, String>()
        val results = mutableMapOf<String, Int>()

        for (instruction in instructions) {
            val parts = instruction.split(" -> ")
            if (parts.size == 2) {
                val (operation, wire) = parts
                if (wire == "b") {
                    wireInstructions[wire] = part1Result.toString()
                } else {
                    wireInstructions[wire] = operation
                }
            }
        }

        return getWireValue("a", results, wireInstructions)
    }

    val input = readInput("Day07")
    val part1Result = part1(input)
    check(part1Result == 46065)

    val part2Result = part2(input, part1Result)
    check(part2Result == 14134)
}