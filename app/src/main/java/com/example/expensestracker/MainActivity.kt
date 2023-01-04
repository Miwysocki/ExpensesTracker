package com.example.expensestracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.data.Expense
import com.example.expensestracker.rv.ExpensesAdapter
import com.example.expensestracker.ui.AddExpenseDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var expenseViewmodel: ExpenseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        expenseViewmodel = ViewModelProvider(this).get(ExpenseViewModel::class.java)

        val recyclerview = findViewById<RecyclerView>(R.id.rvExpenses)

        val adapter = ExpensesAdapter()
        recyclerview.adapter = adapter
        expenseViewmodel.getA().observe(this,
            Observer<List<Expense>> { expenses: List<Expense> -> adapter.setExpenses(expenses) })

        val addBtn = findViewById<FloatingActionButton>(R.id.floatingAddButton)
        addBtn.setOnClickListener{
            val newFragment = AddExpenseDialogFragment()
            newFragment.show(supportFragmentManager, "game")
        }


    }
}