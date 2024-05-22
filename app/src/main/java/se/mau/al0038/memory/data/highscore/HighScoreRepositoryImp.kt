package se.mau.al0038.memory.data.highscore

import android.content.Context
import kotlinx.coroutines.flow.Flow

// Hilt Impl
class HighScoreRepositoryImp(
    private val context: Context,
    private val highScoreDao: HighScoreDao
) : HighScoreRepository {
    override fun getHighScoreHistory(): Flow<List<HighScore>> {
        return highScoreDao.getAllItems()
    }

    override suspend fun insertHighScore(highScore: HighScore) {
        highScoreDao.insert(highScore)
    }
}