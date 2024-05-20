package se.mau.al0038.memory.data

enum class Difficulty(val x: Int, val y: Int) {
    Easy(4, 3),
    Intermediate(4,4),
    Hard(5,4)
}
data class Settings(
    val playerCount: Int = 1,
    val difficulty: Difficulty = Difficulty.Easy,
)