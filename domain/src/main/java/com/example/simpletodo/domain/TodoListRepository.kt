package com.example.simpletodo.domain

import androidx.lifecycle.LiveData

interface TodoListRepository {

    fun addTodoItem(todoItem: TodoItem)

    fun deleteTodoItem(todoItem: TodoItem)

    fun editTodoItem(todoItem: TodoItem)

    fun getTodoItem(todoItemId: Int): TodoItem

    fun getTodoItemList(): LiveData<List<TodoItem>>
}