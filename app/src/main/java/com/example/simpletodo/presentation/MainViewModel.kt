package com.example.simpletodo.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simpletodo.data.TodoListRepositoryImpl
import com.example.simpletodo.domain.DeleteTodoItemUseCase
import com.example.simpletodo.domain.EditTodoItemUseCase
import com.example.simpletodo.domain.GetTodoListUseCase
import com.example.simpletodo.domain.TodoItem

class MainViewModel : ViewModel() {

    private val repository = TodoListRepositoryImpl

    private val getTodoListUseCase = GetTodoListUseCase(repository)
    private val deleteTodoListUseCase = DeleteTodoItemUseCase(repository)
    private val editTodoItemUseCase = EditTodoItemUseCase(repository)

    val todoList = getTodoListUseCase.execute()

    fun deleteTodoItem(todoItem: TodoItem) {
        deleteTodoListUseCase.execute(todoItem)
    }

    // for change state of checkbox on item
    fun changeItemCompleteState(todoItem: TodoItem) {
        val newItem = todoItem.copy(isComplete = !todoItem.isComplete)
        editTodoItemUseCase.execute(newItem)
    }

    fun isEmptyList(): Boolean {
        return todoList.value.isNullOrEmpty()
    }
}