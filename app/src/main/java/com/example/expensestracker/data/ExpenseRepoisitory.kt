package com.example.expensestracker.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class ExpenseRepoisitory(private val dao: ExpenseDao ) {

    suspend fun insertExpense(expense: Expense){
        dao.insert(expense)
    }

    suspend fun updateExpense(expense: Expense){
        dao.update(expense)
    }

    suspend fun deleteExpense(expense: Expense){
        dao.delete(expense)
    }

     fun getAllExpenses() :Flow<List<Expense>>{
       return dao.getAllExpenses()
    }

    fun getA() :LiveData<List<Expense>>{
        return dao.getA()
    }
}