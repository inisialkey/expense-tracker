package com.learning.expensetracker.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learning.expensetracker.data.model.ExpenseModel
import com.learning.expensetracker.databinding.ExpenseItemBinding

class ExpenseAdapter(private val onDeleteClick: (ExpenseModel) -> Unit): RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    private var expenses = listOf<ExpenseModel>()

    inner class ExpenseViewHolder(val binding: ExpenseItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExpenseViewHolder {
        val binding = ExpenseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ExpenseViewHolder,
        position: Int
    ) {
        val expense = expenses[position]
        holder.binding.apply {
            tvTitle.text = expense.title
            tvCategory.text = expense.category
            tvAmount.text = "$${expense.amount}"
            tvDate.text = expense.date
        }

        holder.itemView.setOnLongClickListener {
            onDeleteClick(expense)
            true
        }
    }

    override fun getItemCount() = expenses.size

    fun setExpenses(list: List<ExpenseModel>) {
        expenses = list
        notifyDataSetChanged()
    }
}