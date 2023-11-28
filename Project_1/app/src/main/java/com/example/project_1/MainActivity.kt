package com.example.project_1

import SharedPreferencesUtil
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter
    private lateinit var sharedPreferencesUtil: SharedPreferencesUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferencesUtil = SharedPreferencesUtil(this)

        val savedTodos = sharedPreferencesUtil.loadToDoList()
        todoAdapter = TodoAdapter(savedTodos.toMutableList()){

                sharedPreferencesUtil.saveToDoList(todoAdapter.todos)

        }

        val rvTodoItems: RecyclerView = findViewById(R.id.rvToDoItems)
        rvTodoItems.adapter = todoAdapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)

        val btnAddTodo: Button = findViewById(R.id.btnAddTodo)
        val etTodoTitle: EditText = findViewById(R.id.etToDoTitle)
        btnAddTodo.setOnClickListener {
            val todoTitle = etTodoTitle.text.toString()
            if (todoTitle.isNotEmpty()) {
                val todo = Todo(todoTitle)
                todoAdapter.addTodo(todo)
                etTodoTitle.text.clear()
            }
            sharedPreferencesUtil.saveToDoList(todoAdapter.todos)

        }
        val btnDeleteDoneTools: Button = findViewById(R.id.btnDeleteCompletedTodo)
        btnDeleteDoneTools.setOnClickListener {
            todoAdapter.deleteDoneTodo()
            sharedPreferencesUtil.saveToDoList(todoAdapter.todos)
        }
    }
}