package com.example.simpletodo.presentation

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.AndroidViewModel
import com.example.simpletodo.data.TodoListRepositoryImpl
import com.example.simpletodo.domain.DeleteTodoItemUseCase
import com.example.simpletodo.domain.EditTodoItemUseCase
import com.example.simpletodo.domain.GetTodoListUseCase
import com.example.simpletodo.domain.TodoItem
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TodoListRepositoryImpl(application)

    private val getTodoListUseCase = GetTodoListUseCase(repository)
    private val deleteTodoListUseCase = DeleteTodoItemUseCase(repository)
    private val editTodoItemUseCase = EditTodoItemUseCase(repository)

    val todoList = getTodoListUseCase.execute()

    fun deleteTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            deleteTodoListUseCase.execute(todoItem)
        }
    }

    // for change state of checkbox on item
    fun changeItemCompleteState(todoItem: TodoItem) {
        viewModelScope.launch {
            val newItem = todoItem.copy(isComplete = !todoItem.isComplete)
            editTodoItemUseCase.execute(newItem)
        }
    }

    fun isEmptyList(): Boolean {
        return todoList.value.isNullOrEmpty()
    }
}