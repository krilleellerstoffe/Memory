package se.mau.al0038.memory.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import se.mau.al0038.memory.data.highscore.HighScoreDao
import se.mau.al0038.memory.data.highscore.HighScoreDatabase
import se.mau.al0038.memory.data.highscore.HighScoreRepository
import se.mau.al0038.memory.data.highscore.HighScoreRepositoryImp
import javax.inject.Singleton

// Hilt Impl
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHighScoreDatabase(
        @ApplicationContext context: Context
    ): HighScoreDatabase {
        return Room.databaseBuilder(
            context,
            HighScoreDatabase::class.java,
            "HighScore_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideHighScoreDao(
        highScoreDatabase: HighScoreDatabase
    ): HighScoreDao {
        return highScoreDatabase.highScoreDao()
    }

    @Provides
    @Singleton
    fun provideHighScoreRepository(
        @ApplicationContext context: Context,
        highScoreDao: HighScoreDao
    ): HighScoreRepository {
        return HighScoreRepositoryImp(context, highScoreDao)
    }
}