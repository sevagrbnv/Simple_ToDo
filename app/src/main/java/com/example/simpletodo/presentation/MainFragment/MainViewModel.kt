package com.example.simpletodo.presentation.MainFragment

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import com.example.simpletodo.domain.DeleteTodoItemUseCase
import com.example.simpletodo.domain.EditTodoItemUseCase
import com.example.simpletodo.domain.GetTodoListUseCase
import com.example.simpletodo.domain.TodoItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getTodoListUseCase: GetTodoListUseCase,
    private val deleteTodoListUseCase: DeleteTodoItemUseCase,
    private val editTodoItemUseCase: EditTodoItemUseCase
    ) : ViewModel() {

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
        return todoList.value.let {
            it?.isEmpty() ?: true
        }
    }
}