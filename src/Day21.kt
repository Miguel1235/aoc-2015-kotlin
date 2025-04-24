import kotlin.math.max

fun main() {
    data class Character(val name: String, var hitPoints: Int, val damage: Int, val armor: Int)

    class Game(val player: Character, val boss: Character) {
        var attacker = player
        fun fight(): Boolean {
            while (true) {
                val defender = if (attacker == player) boss else player
                val damageToDeal = max(attacker.damage - defender.armor, 1)
                defender.hitPoints -= damageToDeal
                if (defender.hitPoints <= 0) {
                    return attacker == player
                }
                attacker = defender
            }
        }
    }

    data class Item(val name: String, val cost: Int, val damage: Int = 0, val armor: Int = 0)

    class Shop {
        val weapons = listOf(
            Item("Dagger", 8, 4, 0),
            Item("Shortsword", 10, 5, 0),
            Item("Warhammer", 25, 6, 0),
            Item("Longsword", 40, 7, 0),
            Item("Greataxe", 74, 8, 0)
        )

        val armors = listOf(
            Item("Leather", 13, 0, 1),
            Item("Chainmail", 31, 0, 2),
            Item("Splintmail", 53, 0, 3),
            Item("Bandedmail", 75, 0, 4),
            Item("Platemail", 102, 0, 5)
        )

        val rings = listOf(
            Item("Damage +1", 25, 1, 0),
            Item("Damage +2", 50, 2, 0),
            Item("Damage +3", 100, 3, 0),
            Item("Defense +1", 20, 0, 1),
            Item("Defense +2", 40, 0, 2),
            Item("Defense +3", 80, 0, 3)
        )


        fun generateCombinations(): List<List<Item>> {
            val result = mutableListOf<List<Item>>()

            val ringCombinations = mutableListOf<List<Item>>()

            for (ring in rings) {
                ringCombinations.add(listOf(ring))
            }

            for (i in rings.indices) {
                for (j in i + 1 until rings.size) {
                    ringCombinations.add(listOf(rings[i], rings[j]))
                }
            }

            for (weapon in weapons) {
                for (ringCombo in ringCombinations) {
                    val combo = mutableListOf<Item>()
                    combo.add(weapon)
                    combo.addAll(ringCombo)
                    result.add(combo)
                }

                for (armor in armors) {
                    for (ringCombo in ringCombinations) {
                        val combo = mutableListOf<Item>()
                        combo.add(weapon)
                        combo.add(armor)
                        combo.addAll(ringCombo)
                        result.add(combo)
                    }
                }
            }
            return result
        }
    }


    fun part(isPart2: Boolean = false): Int {
        val shop = Shop()
        val combinations = shop.generateCombinations()
        var minCost = Int.MAX_VALUE
        var maxCost = 0
        for (combination in combinations) {
            val totalDamage = combination.sumOf { it.damage }
            val totalArmor = combination.sumOf { it.armor }
            val totalCost = combination.sumOf { it.cost }

            val main = Character("player", 100, totalDamage, totalArmor)
            val boss = Character("boss", 104, 8, 1)

            val game = Game(main, boss)
            if (game.fight()) {
                minCost = minOf(totalCost, minCost)
            } else {
                maxCost = maxOf(totalCost, maxCost)
            }
        }
        return if (isPart2) maxCost else minCost
    }

    check(part() == 78)
    check(part(true) == 148)
}