package com.example.expensestracker.di

import android.app.Application
import androidx.room.Room
import com.example.expensestracker.data.ExpenseDatabase
import com.example.expensestracker.data.ExpenseRepoisitory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) : ExpenseDatabase{
        return Room.databaseBuilder(
            app,
            ExpenseDatabase::class.java,
            "expenses"
        ).build()
    }

    @Provides
    @Singleton
    fun provideExpenseRepository(db: ExpenseDatabase) : ExpenseRepoisitory{
            return ExpenseRepoisitory(db.dao)
    }
}