package com.example.simpletodo.data

import com.example.simpletodo.domain.TodoItem
import com.example.simpletodo.domain.TodoListRepository

object TodoListRepositoryImpl: TodoListRepository {

    private val todoList = mutableListOf<TodoItem>()

    private var autoIncrementId = 0

    override fun addTodoItem(todoItem: TodoItem) {
        if (todoItem.id == TodoItem.UNDEFINED_ID) {
            todoItem.id = autoIncrementId++
        }
        todoList.add(todoItem)
    }

    override fun deleteTodoItem(todoItem: TodoItem) {
        todoList.remove(todoItem)
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

    override fun getTodoItemList(): List<TodoItem> {
        return todoList.toList()
    }
}