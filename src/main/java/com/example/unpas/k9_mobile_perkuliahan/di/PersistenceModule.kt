package com.example.unpas.k9_mobile_perkuliahan.di

import android.app.Application
import androidx.room.Room
import com.example.unpas.k9_mobile_perkuliahan.persistences.AppDatabase
import com.example.unpas.k9_mobile_perkuliahan.persistences.DosenDao
import com.example.unpas.k9_mobile_perkuliahan.persistences.MahasiswaDao
import com.example.unpas.k9_mobile_perkuliahan.persistences.MatkulDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(
                application,
                AppDatabase::class.java,
                "perkuliahan"
            )
            .fallbackToDestructiveMigration()
            .build()
    }
//    dosen
    @Provides
    @Singleton
    fun provideDosenDao(appDatabase: AppDatabase): DosenDao {
        return appDatabase.dosenDao()
    }

//    mahasiswa
    @Provides
    @Singleton
    fun provideMahasiswaDao(appDatabase: AppDatabase): MahasiswaDao {
        return appDatabase.mahasiswaDao()
    }
//    matkul
    @Provides
    @Singleton
    fun provideMatkulDao(appDatabase: AppDatabase): MatkulDao {
        return appDatabase.matkulDao()
    }
}