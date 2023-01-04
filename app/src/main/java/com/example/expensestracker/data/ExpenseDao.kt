package com.example.expensestracker.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insert(expense: Expense)

    @Update
    suspend fun update(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)

    @Query("SELECT * FROM Expense")
     fun getAllExpenses(): Flow<List<Expense>>

    @Query("SELECT * FROM Expense")
    fun getA(): LiveData<List<Expense>>
}