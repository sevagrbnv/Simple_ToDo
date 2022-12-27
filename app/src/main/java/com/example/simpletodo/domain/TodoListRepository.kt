package com.example.simpletodo.domain

import androidx.lifecycle.LiveData

interface TodoListRepository {

    suspend fun addTodoItem(todoItem: TodoItem)

    suspend fun deleteTodoItem(todoItem: TodoItem)

    suspend fun editTodoItem(todoItem: TodoItem)

    suspend fun getTodoItem(todoItemId: Int): TodoItem

    fun getTodoItemList(): LiveData<List<TodoItem>>
}