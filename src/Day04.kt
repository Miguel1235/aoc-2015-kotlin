import java.security.MessageDigest


fun main() {
    @OptIn(ExperimentalStdlibApi::class)
    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(this.toByteArray())
        return digest.toHexString()
    }

    fun part1(input: String, startingZeros: Int = 5): Int {
        var current = 0
        val target = "0".repeat(startingZeros)
        while(!"$input${current}".md5().startsWith(target)) {
            current++
        }
        return current
    }

    val testInput = readInput("Day04_test").first()
    check(part1(testInput) == 609043)

    val input = readInput("Day04").first()
    check(part1(input) == 254575)
    check(part1(input, 6) == 1038736)
}