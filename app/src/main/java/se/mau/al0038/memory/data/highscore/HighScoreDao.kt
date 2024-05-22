package se.mau.al0038.memory.data.highscore

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HighScoreDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(highScore: HighScore)

    @Query("SELECT * from high_scores ORDER BY attempts ASC, score DESC")
    fun getAllItems(): Flow<List<HighScore>>
}