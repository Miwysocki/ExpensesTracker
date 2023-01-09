package com.example.expensestracker.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.R
import com.example.expensestracker.data.Expense

class ExpensesAdapter(): RecyclerView.Adapter<ExpensesAdapter.MyViewHolder>() {

    private var expensesList: List<Expense> = ArrayList()
    var onItemLongClick: ((Expense) -> Unit)? = null


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView
        val tvType: TextView
        val tvPrice: TextView

        init {
            tvName = view.findViewById(R.id.tvName)
            tvPrice = view.findViewById(R.id.tvPrice)
            tvType = view.findViewById(R.id.tvType)
            itemView.setOnLongClickListener {
                onItemLongClick?.invoke(expensesList[adapterPosition])
                return@setOnLongClickListener true
            }
        }
    }

    fun setExpenses(expensesList: List<Expense>) {
            this.expensesList = expensesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.expense_row, viewGroup, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.tvName.text = expensesList[position].name
        holder.tvPrice.text = expensesList[position].price.toString()
        holder.tvType.text = expensesList[position].type

    }

    override fun getItemCount(): Int {
        return   expensesList.size
    }

}