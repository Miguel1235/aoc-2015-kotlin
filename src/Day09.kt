fun main() {
    data class City(val name: String)

    class Graph(input: List<Triple<City, City, Int>>) {
        val adjacencyMap = mutableMapOf<City, MutableMap<City, Int>>()

        init {
            for(route in input) {
                val (c1, c2, km) = route
                addEdge(c1, c2, km)
            }
        }

        fun addEdge(from: City, to: City, km: Int) {
            adjacencyMap.putIfAbsent(from, mutableMapOf())
            adjacencyMap.putIfAbsent(to, mutableMapOf())

            adjacencyMap[from]?.put(to, km)
            adjacencyMap[to]?.put(from, km)
        }

        fun findShortestPath(): Int {
            val allCities = adjacencyMap.keys.toSet()

            return buildList {
                for(startingCity in allCities) {
                    add(findShortestPath(startingCity))
                }
            }.min()
        }

        fun findLongestPath(): Int {
            val allCities = adjacencyMap.keys.toSet()

            println(
                buildList {
                    for(startingCity in allCities) {
                        add(findLongestPath(startingCity))
                    }
                }
            )

            return buildList {
                for(startingCity in allCities) {
                    add(findLongestPath(startingCity))
                }
            }.max()
        }

        private fun findLongestPath(startCity: City): Int {
            var currentCity = startCity
            val visitedCities = mutableSetOf(currentCity)
            var totalDistance = 0
            while(visitedCities.size < adjacencyMap.size) {
                var furthestCity: City? = null
                var longestDistance = -1
                adjacencyMap[currentCity]?.forEach { (neighbor, distance) ->
                    if(neighbor !in visitedCities && distance > longestDistance) {
                        longestDistance = distance
                        furthestCity = neighbor
                    }
                }
                if(furthestCity != null) {
                    currentCity = furthestCity!!
                    visitedCities.add(currentCity)
                    totalDistance += longestDistance
                } else {
                    break
                }
            }
            return totalDistance
        }

        private fun findShortestPath(startCity: City): Int {
            var currentCity = startCity
            val visitedCities = mutableSetOf(currentCity)
            var totalDistance = 0
            while(visitedCities.size < adjacencyMap.size) {
                var closestCity: City? = null
                var shortestDistance = Int.MAX_VALUE

                adjacencyMap[currentCity]?.forEach { (neighbor, distance) ->
                    if(neighbor !in visitedCities && distance < shortestDistance) {
                        shortestDistance = distance
                        closestCity = neighbor
                    }
                }
                if(closestCity != null) {
                    currentCity = closestCity!!
                    visitedCities.add(currentCity)
                    totalDistance += shortestDistance
                }
            }
            return totalDistance
        }

        override fun toString(): String {
            return adjacencyMap.toString()
        }
    }

    fun parseInput(cities: List<String>): List<Triple<City, City, Int>> {
        val cityRegex = Regex("""(\w+) to (\w+) = (\d+)""")

        return buildList {
            for(city in cities) {
                val (_, c1, c2, km) = cityRegex.find(city)!!.groupValues
                add(Triple(City(c1), City(c2), km.toInt()))
            }
        }
    }

//    val testInput = parseInput(readInput("Day09_test"))
//    val testGraph = Graph(testInput)
//    check(testGraph.findShortestPath() == 605)
//    check(testGraph.findLongestPath() == 982)


    val input = parseInput(readInput("Day09"))
    val graph = Graph(input)
    check(graph.findShortestPath() == 207)

    graph.findLongestPath().println()
}