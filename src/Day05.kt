fun main() {
    fun isNiceString(testString: String): Boolean {
        val vowels = listOf('a', 'e', 'i', 'o', 'u')
        val bannedWords = Regex("""ab|cd|pq|xy""")

        val numberOfVowels = testString.count { it in vowels } >= 3
        val appearsTwice = testString.zipWithNext().count { it.first == it.second } >= 1
        val notBannedWords = !testString.contains(bannedWords)

        return numberOfVowels && appearsTwice && notBannedWords
    }

    val part1 = { input: List<String> ->
        input.fold(0) { acc, c ->
            if (isNiceString(c)) acc + 1 else acc
        }
    }

    fun pairs(word: String): List<Pair<String, String>> {
        return word.windowed(2)
            .flatMapIndexed { pivI, pivWord ->
                word.drop(pivI + 2).windowed(2).map { pivWord to it }
            }
    }

    fun threeEqual(input: String): Boolean {
        for (i in input.indices) {
            val f = input.getOrNull(i - 1)
            val s = input.getOrNull(i + 1)
            if (f == s) return true
        }
        return false
    }

    fun part2(input: List<String>): Int {
        return input.fold(0) { acc, i ->
            acc + if(pairs(i).any { it.first == it.second } && threeEqual(i)) 1 else 0
        }
    }

    val testInput = readInput("Day05_test")
    part1(testInput)
    part2(testInput)

    val input = readInput("Day05")
    check(part1(input) == 258)
    part2(input).println()
}