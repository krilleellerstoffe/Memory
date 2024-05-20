package se.mau.al0038.memory.data

enum class Difficulty{
    Easy,
    Intermediate,
    Hard
}
data class Settings(
    val playerCount: Int = 1,
    val difficulty: Difficulty = Difficulty.Easy,
)