import kotlin.math.max

fun main() {
    data class Ingredient(
        val capacity: Int,
        val durability: Int,
        val flavor: Int,
        val texture: Int,
        val calories: Int
    )

    fun obtainIngredients(input: List<String>): List<Ingredient> {
        val ingredientRegex =
            Regex("""capacity (-?\d+), durability (-?\d+), flavor (-?\d+), texture (-?\d+), calories (-?\d+)""")
        return buildList {
            input.map {
                val (cap, dur, flav, text, cal) = ingredientRegex.find(it)!!.groupValues.drop(1)
                add(Ingredient(cap.toInt(), dur.toInt(), flav.toInt(), text.toInt(), cal.toInt()))
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


    fun part1(input: List<String>, isPart2: Boolean = false): Int {
        val ingredients = obtainIngredients(input)
        val combinations = generateCombinations(ingredients.size, 100)

        return combinations.maxOf { combination ->
            val cap = max(ingredients.indices.sumOf { ingredients[it].capacity * combination[it] }, 0)
            val dur = max(ingredients.indices.sumOf { ingredients[it].durability * combination[it] }, 0)
            val flav = max(ingredients.indices.sumOf { ingredients[it].flavor * combination[it] }, 0)
            val text = max(ingredients.indices.sumOf { ingredients[it].texture * combination[it] }, 0)

            when(isPart2) {
                true -> {
                    if (ingredients.indices.sumOf { ingredients[it].calories * combination[it] } == 500) {
                        cap * dur * flav * text
                    } else {
                        0
                    }
                }
                false -> cap * dur * flav * text
            }
        }
    }

    val testInput = readInput("Day15_test")
    check(part1(testInput) == 62842880)
    check(part1(testInput, true) == 57600000)

    val input = readInput("Day15")
    check(part1(input) == 21367368)
    check(part1(input, true) == 1766400)
}