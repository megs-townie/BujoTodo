package com.example.project_1

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter (
    val todos: MutableList<Todo>,
    private val onTodoChanged: () -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }

    fun addTodo(todo: Todo) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    fun deleteDoneTodo() {
        todos.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikethrough(tvToDoTitle: TextView, isChecked: Boolean) {
        if(isChecked) {
            tvToDoTitle.paintFlags = tvToDoTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            tvToDoTitle.paintFlags = tvToDoTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = todos[position]
        holder.itemView.apply {
            val tvToDoTitle: TextView = findViewById(R.id.tvTodoTitle)
            val cbDone: CheckBox = findViewById(R.id.cbCompleted) // Corrected
            tvToDoTitle.text = curTodo.title
            cbDone.isChecked = curTodo.isChecked
            toggleStrikethrough(tvToDoTitle, curTodo.isChecked)

            // Corrected: Removed the duplicate listener and moved the existing one inside apply
            cbDone.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikethrough(tvToDoTitle, isChecked)
                curTodo.isChecked = isChecked
                onTodoChanged() // Call the callback when an item changes
            }
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}