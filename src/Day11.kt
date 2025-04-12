fun main() {
    fun isValidPassword(password: String): Boolean {
        val abecedary = "abcdefghijklmnopqrsutvwxyz"
        val straightLetters = abecedary.windowed(3)
        val bannedRegex = Regex("""[iol]""")
        val includesStraight = straightLetters.any { it in password }
        val includesBannedLetter = password.contains(bannedRegex)
        val includesPair = password.windowed(2)
            .flatMapIndexed { pivI, pivWord ->
                password.drop(pivI + 2).windowed(2).map { pivWord to it }
            }
            .count {
                val f = it.first.toList()
                val s = it.second.toList()
                f[0] == f[1] && s[0] == s[1]
            } >= 1
        return includesStraight && !includesBannedLetter && includesPair
    }

    val generateNextLetter = { letter: Char -> if (letter.lowercaseChar() == 'z') 'a' else (letter.lowercaseChar().code + 1).toChar() }

    fun generateNextPassword(password: String): String {
        val pwdList = password.toMutableList()
        var cursor = password.length - 1
        while (cursor >= 0) {
            val letter = password[cursor]
            val nextLetter = generateNextLetter(letter)
            pwdList[cursor] = nextLetter
            if (nextLetter != 'a') break
            cursor--
        }
        return pwdList.joinToString("")
    }

    fun part1(input: String): String {
        var currentPassword = input
        while(!isValidPassword(currentPassword)) {
            currentPassword = generateNextPassword(currentPassword)
        }
        return currentPassword
    }

    val input = readInput("Day11").first()
    check(part1(input) == "cqjxxyzz")
    check(part1(generateNextPassword("cqjxxyzz")) == "cqkaabcc")
}