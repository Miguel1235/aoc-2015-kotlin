import kotlin.math.max

fun main() {
    data class Ingredient(
        val name: String,
        val capacity: Int,
        val durability: Int,
        val flavor: Int,
        val texture: Int,
        val calories: Int
    )

    fun obtainIngredients(input: List<String>): List<Ingredient> {
        val ingredientRegex =
            Regex("""(\w+): capacity (-?\d+), durability (-?\d+), flavor (-?\d+), texture (-?\d+), calories (-?\d+)""")
        return buildList {
            input.map {
                val r = ingredientRegex.find(it)!!.groupValues.drop(1)
                add(Ingredient(r[0], r[1].toInt(), r[2].toInt(), r[3].toInt(), r[4].toInt(), r[5].toInt()))
            }
        }
    }

    fun generateCombinations(listSize: Int, targetSum: Int): List<List<Int>> {
        val results = mutableListOf<List<Int>>()
        val currentCombination = MutableList(listSize) { 0 }

        fun generateCombinationsRecursive(index: Int, remainingSum: Int) {
            if (index == listSize - 1) {
                currentCombination[index] = remainingSum
                results.add(currentCombination.toList())
                return
            }

            for (value in 0..remainingSum) {
                currentCombination[index] = value
                generateCombinationsRecursive(index + 1, remainingSum - value)
            }
        }

        generateCombinationsRecursive(0, targetSum)
        return results
    }


    fun part1(input: List<String>, isPart2: Boolean = false): Long {
        val ingredients = obtainIngredients(input)
        val combinations = generateCombinations(ingredients.size, 100)

        return combinations.maxOf { combination ->
            val cap = max(ingredients.indices.sumOf { ingredients[it].capacity.toLong() * combination[it].toLong() }, 0L)
            val dur = max(ingredients.indices.sumOf { ingredients[it].durability.toLong() * combination[it].toLong() }, 0L)
            val flav = max(ingredients.indices.sumOf { ingredients[it].flavor.toLong() * combination[it].toLong() }, 0L)
            val text = max(ingredients.indices.sumOf { ingredients[it].texture.toLong() * combination[it].toLong() }, 0L)


            if(isPart2) {
                if(ingredients.indices.sumOf { ingredients[it].calories.toLong() * combination[it].toLong() } == 500L) {
                    cap * dur * flav * text
                } else {
                    0
                }
            } else {
                cap * dur * flav * text
            }
        }
    }

    val testInput = readInput("Day15_test")
    check(part1(testInput) == 62842880L)
    check(part1(testInput, true) == 57600000L)

    val input = readInput("Day15")
    check(part1(input) == 21367368L)
    check(part1(input, true) == 1766400L)
}