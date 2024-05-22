package se.mau.al0038.memory.data.highscore

import android.content.Context
import android.util.Log
import se.mau.al0038.memory.data.PlayerStats

// Hilt Impl
class HighScoreRepositoryImp(
    private val context: Context
) : HighScoreRepository {
    override fun getHighScoreHistory(): List<PlayerStats> {
        // Context is only used for testing DI
        Log.d("HighScoreRepositoryImp", "App name: ${context.applicationInfo.name}")
        return listOf(PlayerStats(1, 1, 1))
    }
}