package com.example.expensestracker.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.expensestracker.ExpenseViewModel
import com.example.expensestracker.data.Expense
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteExpenseDialogFragment(val expense: Expense) : DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val expenseViewmodel = ViewModelProvider(this).get(ExpenseViewModel::class.java)
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Do you want to delete this?")
                .setPositiveButton("delete",
                    DialogInterface.OnClickListener { _, _ ->
                        expenseViewmodel.deleteExpense(expense)
                    })
                .setNegativeButton("cancel",
                    DialogInterface.OnClickListener { _, _ ->
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}