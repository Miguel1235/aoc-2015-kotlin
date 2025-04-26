import kotlin.math.max

fun main() {
    fun part(input: List<String>, regStart: Int = 0): Long {
        val basicInstructionsRegex = Regex("""^(hlf|tpl|inc) ([ab])$""")
        val jmpRegex = Regex("""^jmp ([+-]\d+)$""")
        val jiRegex = Regex("""^(jio|jie) ([ab]), ([+-]\d+)$""")

        var instructionCounter = 0
        var regA = regStart.toLong()
        var regB = 0L

        while (instructionCounter < input.size) {
            val line = input[instructionCounter]

            regA = max(regA, 0)
            regB = max(regB, 0)

            val basicIns = basicInstructionsRegex.find(line)?.groupValues

            if (basicIns != null) {
                val (_, op, r) = basicIns
                when (op) {
                    "inc" -> if (r == "a") regA++ else regB++
                    "tpl" -> if (r == "a") regA *= 3 else regB *= 3
                    "hlf" -> if (r == "a") regA /= 2 else regB /= 2
                }
                instructionCounter++
                continue
            }
            val jump = jmpRegex.find(line)?.groupValues

            if (jump != null) {
                val (_, offset) = jump
                instructionCounter += offset.toInt()
                continue
            }

            val ji = jiRegex.find(line)?.groupValues
            if (ji != null) {
                val (_, op, r, offset) = ji
                val reg = if (r == "a") regA else regB

                if ((op == "jio" && reg == 1L) || (op == "jie" && reg % 2 == 0L)) {
                    instructionCounter += offset.toInt()
                    continue
                }
            }
            instructionCounter++
        }
        return regB
    }

    val testInput = readInput("Day23_test")
    check(part(testInput) == 0L)

    val input = readInput("Day23")
    check(part(input) == 170L)
    check(part(input, 1) == 247L)
}