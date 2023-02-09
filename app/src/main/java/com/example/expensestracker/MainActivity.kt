package com.example.expensestracker


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
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
import androidx.appcompat.app.AlertDialog


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var expenseViewmodel: ExpenseViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        expenseViewmodel = ViewModelProvider(this).get(ExpenseViewModel::class.java)

        setRecyclerview()
        setPieChart()
        setFloatingButton()
        setProgressBar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.simple_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.reset-> {
                resetAll()
                true
            }
            R.id.resetLimit-> {
                resetLimit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun resetLimit() {
        expenseViewmodel.setExpensesLimit(this,0)
        setProgressBar()
    }

    private fun resetAll() {
        val alertDialog: AlertDialog = AlertDialog.Builder(this@MainActivity).create()
        alertDialog.setTitle("Reset")
        alertDialog.setMessage("Are you sure?")
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, "RESET",
            { _, _ -> expenseViewmodel.deleteAll()
            resetLimit()})
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, "CANCEL",
             { dialog, _ -> dialog.dismiss() })
        alertDialog.show()
    }

    private fun setProgressBar() {
        val limitButton = findViewById<Button>(R.id.setLimitBtn)
        val limitEditText = findViewById<EditText>(R.id.limitEditText)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        limitButton.setOnClickListener {
            var limit = limitEditText.text.toString().toInt()
            expenseViewmodel.setExpensesLimit(this, limit)
            progressBar.visibility = View.VISIBLE
            limitButton.visibility = View.INVISIBLE
            limitEditText.visibility = View.INVISIBLE
            progressBar.max = limit
        }

        val expensesLimit = expenseViewmodel.getExpensesLimit(this)
        if (expensesLimit == 0) {
            progressBar.visibility = View.INVISIBLE
            limitButton.visibility = View.VISIBLE
            limitEditText.visibility = View.VISIBLE
        } else progressBar.max = expensesLimit


        expenseViewmodel.getTotalAmount()
            .observe(this, Observer<Int> {  it?.let {totalCosts: Int -> progressBar.progress = totalCosts} })
    }


    private fun setFloatingButton() {
        val addBtn = findViewById<FloatingActionButton>(R.id.floatingAddButton)
        addBtn.setOnClickListener {
            val newFragment = AddExpenseDialogFragment()
            newFragment.show(supportFragmentManager, "")
        }
    }

    private fun setPieChart() {
        val pieChart = findViewById<PieChart>(R.id.pieChart)
        expenseViewmodel.getAllExpenses().observe(this,
            Observer<List<Expense>> { expenses: List<Expense> -> showPieChart(pieChart, expenses) })
    }


    private fun showPieChart(pieChart: PieChart, expenses: List<Expense>) {
        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = "type"


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
        expenseViewmodel.getAllExpenses().observe(this,
            Observer<List<Expense>> { expenses: List<Expense> -> adapter.setExpenses(expenses) })
    }
}