package com.dinesh.contactbook.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.dinesh.contactbook.model.database.ContactBookDataBase
import com.dinesh.contactbook.model.repository.ContactRepository
import com.dinesh.contactbook.model.repository.ContactRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContactBookModule {

    @Provides
    @Singleton
    fun provideContactBookDataBase(
        application: Application
    ): ContactBookDataBase {
        return Room.databaseBuilder(
            application,
            ContactBookDataBase::class.java,
            ContactBookDataBase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideContactRepository(
        dataBase: ContactBookDataBase,
        @ApplicationContext context: Context
    ): ContactRepository {
        return ContactRepositoryImpl(
            dataBase.contactDao, context)
    }
}