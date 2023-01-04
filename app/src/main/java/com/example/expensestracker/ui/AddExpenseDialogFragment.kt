package com.example.expensestracker.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
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
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            val view =inflater.inflate(R.layout.add_expense_dialog, null)
            val expenseViewmodel = ViewModelProvider(this).get(ExpenseViewModel::class.java)

            val nameEditText = view.findViewById<EditText>(R.id.editTextName)
            val priceEditText = view.findViewById<EditText>(R.id.editTextPrice)

            builder.setView(view)
                .setPositiveButton("add",
                    DialogInterface.OnClickListener { dialog, id ->
                        val e = Expense(nameEditText.text.toString(),"other",priceEditText.text.toString().toDouble() )
                        expenseViewmodel.addExpense(e)
                    })
                .setNegativeButton("cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}