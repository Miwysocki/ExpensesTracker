package com.example.expensestracker


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.data.Expense
import com.example.expensestracker.rv.ExpensesAdapter
import com.example.expensestracker.ui.AddExpenseDialogFragment
import com.example.expensestracker.ui.DeleteExpenseDialogFragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var expenseViewmodel: ExpenseViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        expenseViewmodel = ViewModelProvider(this).get(ExpenseViewModel::class.java)

        setRecyclerview()

        val pieChart = findViewById<PieChart>(R.id.pieChart)
        expenseViewmodel.getA().observe(this,
            Observer<List<Expense>> { expenses: List<Expense> ->  showPieChart(pieChart,expenses) })

        val addBtn = findViewById<FloatingActionButton>(R.id.floatingAddButton)
        addBtn.setOnClickListener{
            val newFragment = AddExpenseDialogFragment()
            newFragment.show(supportFragmentManager, "")
        }
    }


    private fun showPieChart(pieChart: PieChart, expenses: List<Expense>) {
        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = "type"

        //initializing data
        val typeAmountMap: MutableMap<String, Int> = HashMap()
        val types = resources.getStringArray(R.array.expense_types_array)

        for(type in types){
            val filtredList = expenses.filter { it.type.lowercase().equals(type.lowercase()) }
            val amount = filtredList?.sumOf { it.price }
            if (amount != null) {
                typeAmountMap[type] = amount.toInt()
            }
            if(amount == 0.0){
                typeAmountMap.remove(type)
            }
        }

        //input data and fit data into pie chart entry
        for (type in typeAmountMap.keys) {
            pieEntries.add(PieEntry(typeAmountMap[type]!!.toFloat(), type))
        }

        //collecting the entries with label name
        val pieDataSet = PieDataSet(pieEntries, label)
        //setting text size of the value
        pieDataSet.valueTextSize = 12f
        //providing color list for coloring different entries
        val c = ColorTemplate.JOYFUL_COLORS.toList()
        pieDataSet.colors = c


        //grouping the data set from entry to chart
        val pieData = PieData(pieDataSet)
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true)
        pieChart.setData(pieData)
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.invalidate()
    }

    private fun setRecyclerview() {
        val recyclerview = findViewById<RecyclerView>(R.id.rvExpenses)
        val adapter = ExpensesAdapter()
        adapter.onItemLongClick = { expense ->
            val newFragment = DeleteExpenseDialogFragment(expense)
            newFragment.show(supportFragmentManager, "")
        }
        recyclerview.adapter = adapter
        expenseViewmodel.getA().observe(this,
            Observer<List<Expense>> { expenses: List<Expense> -> adapter.setExpenses(expenses) })
    }
}