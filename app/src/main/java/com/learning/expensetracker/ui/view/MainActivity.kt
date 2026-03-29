package com.learning.expensetracker.ui.view

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.learning.expensetracker.data.model.ExpenseModel
import com.learning.expensetracker.databinding.ActivityMainBinding
import com.learning.expensetracker.databinding.DialogAddExpenseBinding
import com.learning.expensetracker.ui.adapter.ExpenseAdapter
import com.learning.expensetracker.ui.viewModel.ExpenseViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ExpenseViewModel
    private lateinit var adapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ExpenseAdapter { expense ->
            viewModel.delete(expense)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]

        viewModel.allExpenses.observe(this) {
            adapter.setExpenses(it)
        }

        viewModel.totalAmount.observe(this) { total ->
            binding.tvTotal.text = "Total: $${String.format("%.2f", total ?: 0.0)}"
        }

        binding.fabAdd.setOnClickListener {
            showAddDialog()
        }

    }

    private fun showAddDialog() {
        val dialogBinding = DialogAddExpenseBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Add Expense")
            .setView(dialogBinding.root)
            .setPositiveButton("Add") { _, _ ->
                val title = dialogBinding.etTitle.text.toString()
                val amount = dialogBinding.etAmount.text.toString().toDoubleOrNull() ?: 0.0
                val category = dialogBinding.etCategory.text.toString()
                val date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())

                val expense = ExpenseModel(title = title, amount = amount, category = category, date = date)
                viewModel.insert(expense)
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }
}