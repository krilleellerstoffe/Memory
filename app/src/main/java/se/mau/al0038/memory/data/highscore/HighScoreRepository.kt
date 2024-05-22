package se.mau.al0038.memory.data.highscore

import se.mau.al0038.memory.data.PlayerStats

// Hilt Impl
// Interface is technically not necessary, AppModule could just return the implementation
// and view model could take that instead of interface
interface HighScoreRepository {
    fun getHighScoreHistory(): List<PlayerStats>
}