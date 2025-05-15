package com.peraz.notesapp.di

import android.content.Context
import androidx.room.Room
import com.peraz.notesapp.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, "AppDatabase"
        ).build()
    }//Crea el BUilder para la base de datos.

    @Singleton
    @Provides
    fun providenotesDao(db: AppDatabase) = db.notesDao()
    //COn este se provee todo el pex al parecer
}