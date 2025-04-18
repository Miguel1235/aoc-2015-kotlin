fun main() {
    fun part(input: List<String>, isPart2: Boolean = false): Int {
        data class Sue(
            val number: Int,
            val children: Int?,
            val cats: Int?,
            val samoyeds: Int?,
            val pomeranians: Int?,
            val akitas: Int?,
            val vizslas: Int?,
            val goldfish: Int?,
            val trees: Int?,
            val cars: Int?,
            val perfumes: Int?
        )

        val compoundsRegex = Regex("""(\w+): (\d+)""")
        val sues = input.mapIndexed { idx, line ->
            val properties = compoundsRegex.findAll(line)
                .associate { it.groupValues[1] to it.groupValues[2].toInt() }
            Sue(
                number = idx + 1,
                children = properties["children"],
                cats = properties["cats"],
                samoyeds = properties["samoyeds"],
                pomeranians = properties["pomeranians"],
                akitas = properties["akitas"],
                vizslas = properties["vizslas"],
                goldfish = properties["goldfish"],
                trees = properties["trees"],
                cars = properties["cars"],
                perfumes = properties["perfumes"]
            )
        }

        val sue2Find = Sue(
            number = -1,
            children = 3,
            cats = 7,
            samoyeds = 2,
            pomeranians = 3,
            akitas = 0,
            vizslas = 0,
            goldfish = 5,
            trees = 3,
            cars = 2,
            perfumes = 1
        )

       return sues.find {
           (it.children == sue2Find.children || it.children == null) &&
                   (it.samoyeds == sue2Find.samoyeds || it.samoyeds == null) &&
                   (it.akitas == sue2Find.akitas || it.akitas == null) &&
                   (it.vizslas == sue2Find.vizslas || it.vizslas == null) &&
                   (it.cars == sue2Find.cars || it.cars == null) &&
                   (it.perfumes == sue2Find.perfumes || it.perfumes == null) &&

                   if (isPart2) {
                       ((it.pomeranians != null && it.pomeranians < sue2Find.pomeranians!!) || it.pomeranians == null) &&
                               ((it.goldfish != null && it.goldfish < sue2Find.goldfish!!) || it.goldfish == null) &&
                               ((it.cats != null && it.cats > sue2Find.cats!!) || it.cats == null) &&
                               ((it.trees != null && it.trees > sue2Find.trees!!) || it.trees == null)

                   } else {
                       (it.pomeranians == sue2Find.pomeranians || it.pomeranians == null) &&
                               (it.goldfish == sue2Find.goldfish || it.goldfish == null) &&
                               (it.cats == sue2Find.cats || it.cats == null) &&
                               (it.trees == sue2Find.trees || it.trees == null)
                   }
       }
           ?.number ?: 0
    }

    val input = readInput("Day16")

    check(part(input) == 373)
    check(part(input, true) == 260)
}