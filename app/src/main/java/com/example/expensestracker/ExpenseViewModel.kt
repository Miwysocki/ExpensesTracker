package com.example.expensestracker



import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensestracker.data.Expense
import com.example.expensestracker.data.ExpenseRepoisitory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val repository: ExpenseRepoisitory
) : ViewModel() {



      fun addExpense(e: Expense){
        viewModelScope.launch{
            repository.insertExpense(e)
        }
    }

    fun deleteExpense(e: Expense){
        viewModelScope.launch {
            repository.deleteExpense(e)
        }
    }


      fun getAllExpenses() : LiveData<List<Expense>> {
        return repository.getAllExpenses()
    }

    fun deleteAll(){
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun getTotalAmount() : LiveData<Int>{
        return repository.getTotalAmount()
    }

    fun setExpensesLimit(activity: Activity,limit: Int){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putInt("limit", limit)
            apply()
        }
    }

    fun getExpensesLimit(activity: Activity): Int {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
       return sharedPref?.getInt("limit",0) ?: return 0
    }

}