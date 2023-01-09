package com.example.expensestracker



import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensestracker.data.Expense
import com.example.expensestracker.data.ExpenseRepoisitory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

import javax.inject.Inject


@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val repository: ExpenseRepoisitory
) : ViewModel() {

    val expenses = repository.getAllExpenses()


    public  fun addExpense(e: Expense){
        viewModelScope.launch{
            repository.insertExpense(e)
        }
    }

    fun deleteExpense(e: Expense){
        viewModelScope.launch {
            repository.deleteExpense(e)
        }
    }


    public  fun getAllExpenses() : Flow<List<Expense>> {
        return repository.getAllExpenses()
    }

    public  fun getA() : LiveData<List<Expense>> {
        return repository.getA()
    }

}