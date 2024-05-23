package se.mau.al0038.memory.data

import se.mau.al0038.memory.R

enum class Difficulty(val stringId: Int, val x: Int, val y: Int) {
    Easy(R.string.easy, 4, 3),
    Intermediate(R.string.intermediate, 4,4),
    Hard(R.string.hard, 5,4);

    companion object {
        fun fromStringId(stringId: Int): Difficulty {
            return when(stringId) {
                R.string.easy -> Easy
                R.string.intermediate -> Intermediate
                R.string.hard -> Hard
                else -> throw IllegalArgumentException("No difficulty with stringId $stringId")
            }
        }
    }
}

enum class PlayerCount(val stringId: Int, val count: Int) {
    One(R.string.one, 1),
    Two(R.string.two, 2),
    Three(R.string.three, 3);

    companion object {
        fun fromStringId(stringId: Int): PlayerCount {
            return when(stringId) {
                R.string.one -> One
                R.string.two -> Two
                R.string.three -> Three
                else -> throw IllegalArgumentException("No playerCount with stringId $stringId")
            }
        }
    }
}
data class Settings(
    val playerCount: PlayerCount = PlayerCount.One,
    val difficulty: Difficulty = Difficulty.Easy,
)