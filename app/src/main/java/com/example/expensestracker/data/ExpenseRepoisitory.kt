package com.example.expensestracker.data

import androidx.lifecycle.LiveData


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

    fun getAllExpenses() :LiveData<List<Expense>>{
        return dao.getA()
    }

    fun getTotalAmount(): LiveData<Int>{
        return dao.getTotalAmount()
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }
}