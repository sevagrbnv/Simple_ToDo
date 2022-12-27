package com.example.simpletodo.presentation

import android.app.Application
import androidx.lifecycle.*
import com.example.simpletodo.data.TodoListRepositoryImpl
import com.example.simpletodo.domain.AddTodoItemUseCase
import com.example.simpletodo.domain.EditTodoItemUseCase
import com.example.simpletodo.domain.GetTodoItemUseCase
import com.example.simpletodo.domain.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TodoListRepositoryImpl(application)

    private val getTodoItemUseCase = GetTodoItemUseCase(repository)
    private val addTodoItemUseCase = AddTodoItemUseCase(repository)
    private val editTodoItemUseCase = EditTodoItemUseCase(repository)

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
        val validInput = isValidInput(desc)
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
        val validInput = isValidInput(desc)
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

    private fun isValidInput(inputDesc: String): Boolean {
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