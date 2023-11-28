import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.project_1.Todo


class SharedPreferencesUtil(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("ToDoList", Context.MODE_PRIVATE)

    fun saveToDoList(todos: List<Todo>) {
        val gson = Gson()
        val jsonTodos = gson.toJson(todos)
        sharedPreferences.edit().putString("todos", jsonTodos).apply()
    }

    fun loadToDoList(): List<Todo> {
        val gson = Gson()
        val jsonTodos = sharedPreferences.getString("todos", null)
        val type = object : TypeToken<List<Todo>>() {}.type
        return gson.fromJson(jsonTodos, type) ?: emptyList()
    }
}
