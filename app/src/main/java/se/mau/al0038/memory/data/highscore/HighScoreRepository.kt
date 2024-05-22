package se.mau.al0038.memory.data.highscore

import kotlinx.coroutines.flow.Flow

// Hilt Impl
// Interface is technically not necessary, AppModule could just return the implementation
// and view model could take that instead of interface
interface HighScoreRepository {
    fun getHighScoreHistory(): Flow<List<HighScore>>
    suspend fun insertHighScore(highScore: HighScore)
}