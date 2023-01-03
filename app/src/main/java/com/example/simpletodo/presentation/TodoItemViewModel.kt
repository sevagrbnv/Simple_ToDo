package com.example.simpletodo.presentation

import android.app.Application
import androidx.lifecycle.*
import com.example.simpletodo.data.TodoListRepositoryImpl
import com.example.simpletodo.domain.AddTodoItemUseCase
import com.example.simpletodo.domain.EditTodoItemUseCase
import com.example.simpletodo.domain.GetTodoItemUseCase
import com.example.simpletodo.domain.TodoItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class TodoItemViewModel @Inject constructor(
    private val getTodoItemUseCase: GetTodoItemUseCase,
    private val addTodoItemUseCase: AddTodoItemUseCase,
    private val editTodoItemUseCase: EditTodoItemUseCase
) : ViewModel() {

    private val _errorInputDesc = MutableLiveData<Boolean>()
    val errorInputDesc: LiveData<Boolean>
        get() = _errorInputDesc

    private val _todoItem = MutableLiveData<TodoItem>()
    val todoItem: LiveData<TodoItem>
        get() = _todoItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getTodoItem(todoItemId: Int) {
        viewModelScope.launch {
            val item = getTodoItemUseCase.execute(todoItemId)
            _todoItem.value = item
        }
    }

    fun addTodoItem(inputDesc: String?, isHigh: Boolean) {
        val desc = parseDesc(inputDesc)
        val validInput = isValidInput(desc, isHigh)
        if (validInput) {
            viewModelScope.launch {
                val item = TodoItem(desc = desc, isHigh = isHigh, isComplete = false)
                addTodoItemUseCase.execute(item)
                finishWork()
            }
        }
    }

    fun editTodoItem(inputDesc: String?, isHigh: Boolean) {
        val desc = parseDesc(inputDesc)
        val validInput = isValidInput(desc, isHigh)
        if (validInput) {
            _todoItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(desc = desc, isHigh = isHigh)
                    editTodoItemUseCase.execute(item)
                    finishWork()
                }
            }

        }
    }

    private fun parseDesc(inputDesc: String?): String {
        return inputDesc?.trim() ?: ""
    }

    private fun isValidInput(inputDesc: String, isHigh: Boolean): Boolean {
        // if the data model becomes more complex
        var result = true
        if (inputDesc.isBlank())  {
            _errorInputDesc.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputDesc() {
        _errorInputDesc.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}