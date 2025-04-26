enum class Register { A, B }
enum class InstructionType { HLF, TPL, INC, JMP, JIE, JIO }

fun main() {
    data class Instruction(
        val type: InstructionType,
        val register: Register? = null,
        val offset: Int? = null
    )

    fun parseInstruction(line: String): Instruction {
        val basicMatch = Regex("""^(hlf|tpl|inc) ([ab])$""").find(line)?.groupValues
        if (basicMatch != null) {
            val (_, op, r) = basicMatch
            val register = if (r == "a") Register.A else Register.B
            val type = InstructionType.valueOf(op.uppercase())
            return Instruction(type, register)
        }

        val jmpMatch = Regex("""^jmp ([+-]\d+)$""").find(line)?.groupValues
        if (jmpMatch != null) {
            val (_, offset) = jmpMatch
            return Instruction(InstructionType.JMP, offset = offset.toInt())
        }

        val jiMatch = Regex("""^(jio|jie) ([ab]), ([+-]\d+)$""").find(line)?.groupValues
        if (jiMatch != null) {
            val (_, op, r, offset) = jiMatch
            val register = if (r == "a") Register.A else Register.B
            val type = InstructionType.valueOf(op.uppercase())
            return Instruction(type, register, offset.toInt())
        }
        throw IllegalArgumentException("Invalid instruction: $line")
    }

    fun part(instructions: List<Instruction>, aStart: Long = 0L): Long {
        var instructionPointer = 0
        val registers = mutableMapOf(Register.A to aStart, Register.B to 0L)

        while (instructionPointer < instructions.size) {
            val instruction = instructions[instructionPointer]
            when (instruction.type) {
                InstructionType.HLF -> {
                    registers[instruction.register!!] = registers[instruction.register]!! / 2
                    instructionPointer++
                }
                InstructionType.TPL -> {
                    registers[instruction.register!!] = registers[instruction.register]!! * 3
                    instructionPointer++
                }
                InstructionType.INC -> {
                    registers[instruction.register!!] = registers[instruction.register]!! + 1
                    instructionPointer++
                }
                InstructionType.JMP -> {
                    instructionPointer += instruction.offset!!
                }
                InstructionType.JIE -> {
                    val value = registers[instruction.register!!]!!
                    instructionPointer += if (value % 2 == 0L) instruction.offset!! else 1
                }
                InstructionType.JIO -> {
                    val value = registers[instruction.register!!]!!
                    instructionPointer += if (value == 1L) instruction.offset!! else 1
                }
            }
        }
        return registers[Register.B] ?: 0L
    }

    val testInput = readInput("Day23_test")
    val testInstructions = testInput.map { parseInstruction(it) }
    check(part(testInstructions) == 0L)

    val input = readInput("Day23")
    val instructions = input.map { parseInstruction(it) }
    check(part(instructions) == 170L)
    check(part(instructions, 1) == 247L)
}