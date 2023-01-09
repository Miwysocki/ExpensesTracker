package com.example.expensestracker.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.expensestracker.ExpenseViewModel
import com.example.expensestracker.R
import com.example.expensestracker.data.Expense
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddExpenseDialogFragment: DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            val view =inflater.inflate(R.layout.add_expense_dialog, null)
            val expenseViewmodel = ViewModelProvider(this).get(ExpenseViewModel::class.java)

            val nameEditText = view.findViewById<EditText>(R.id.editTextName)
            val priceEditText = view.findViewById<EditText>(R.id.editTextPrice)

            val spinner: Spinner = view.findViewById(R.id.spinnerType)
            spinner.setSelection(0);
            ArrayAdapter.createFromResource(
                view.context,
                R.array.expense_types_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }


            builder.setView(view)
                .setPositiveButton("add",
                    DialogInterface.OnClickListener { _, _ ->
                        val e = Expense(nameEditText.text.toString(),spinner.getSelectedItem().toString(),priceEditText.text.toString().toDouble() )
                        expenseViewmodel.addExpense(e)
                    })
                .setNegativeButton("cancel",
                    DialogInterface.OnClickListener { _, _ ->
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}