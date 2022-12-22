package com.example.simpletodo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simpletodo.domain.TodoItem
import com.example.simpletodo.domain.TodoListRepository
import kotlin.random.Random

object TodoListRepositoryImpl: TodoListRepository {

    private val todoListLD = MutableLiveData<List<TodoItem>>()
    private val todoList = sortedSetOf<TodoItem>({o1, o2 -> o1.id.compareTo(o2.id)})

    private var autoIncrementId = 0

    init {
        for (i in 0..10) {
            val item = TodoItem(desc = "Text #$i",
                isHigh = Random.nextBoolean(),
                isComplete = Random.nextBoolean())
            addTodoItem(item)
        }
    }

    override fun addTodoItem(todoItem: TodoItem) {
        if (todoItem.id == TodoItem.UNDEFINED_ID) {
            todoItem.id = autoIncrementId++
        }
        todoList.add(todoItem)
        updateList()
    }

    override fun deleteTodoItem(todoItem: TodoItem) {
        todoList.remove(todoItem)
        updateList()
    }

    override fun editTodoItem(todoItem: TodoItem) {
        val oldItem = getTodoItem(todoItem.id)
        todoList.remove(oldItem)
        addTodoItem(todoItem)
    }

    override fun getTodoItem(todoItemId: Int): TodoItem {
        return todoList.find {
            it.id == todoItemId
        } ?: throw java.lang.RuntimeException("Item with id $todoItemId not found")
    }

    override fun getTodoItemList(): LiveData<List<TodoItem>> {
        return todoListLD
    }

    private fun updateList() {
        todoListLD.value = todoList.toList()
    }
}