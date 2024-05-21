package se.mau.al0038.memory.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import se.mau.al0038.memory.data.highscore.HighScoreRepository
import se.mau.al0038.memory.data.highscore.HighScoreRepositoryImp
import javax.inject.Singleton

// Hilt Impl
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStorageRepository(
        @ApplicationContext context: Context
    ): HighScoreRepository {
        return HighScoreRepositoryImp(context)
    }
}