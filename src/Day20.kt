fun main() {
    fun factors(number: Int): List<Int> {
        return buildList {
            for(i in 1..number) {
                if(number%i == 0) {
                    add(i)
                }
            }
        }
    }

    fun part1(target: Int): Int {
        val obtainPresents = generateSequence (830600) {
            val result = factors(it).sum() * 10
            if(result <= target ) {
                it + 1
            } else {
                null
            }
        }
        return obtainPresents.last()
    }

    check(part1(36000000) == 831600)
}