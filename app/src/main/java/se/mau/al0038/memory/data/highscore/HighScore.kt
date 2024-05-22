package se.mau.al0038.memory.data.highscore

import androidx.room.Entity
import androidx.room.PrimaryKey
import se.mau.al0038.memory.data.PlayerStats

@Entity(tableName = "high_scores")
data class HighScore(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String = "",
    val score: Int = 0,
    val attempts: Int = 0,
    val maxStreak: Int = 0
) {
    companion object {
        fun fromPlayerStats(name: String, playerStats: PlayerStats): HighScore {
            return HighScore(
                name = name,
                score = playerStats.score,
                attempts = playerStats.attempts,
                maxStreak = playerStats.maxStreak
            )
        }
    }
}

