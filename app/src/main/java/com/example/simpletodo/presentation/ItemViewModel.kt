package com.example.simpletodo.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemViewModel : ViewModel() {

    private val repository = com.example.simpletodo.data.TodoListRepositoryImpl

    private val getTodoItemUseCase = com.example.simpletodo.domain.GetTodoItemUseCase(repository)
    private val addTodoItemUseCase = com.example.simpletodo.domain.AddTodoItemUseCase(repository)
    private val editTodoItemUseCase = com.example.simpletodo.domain.EditTodoItemUseCase(repository)

    private val _errorInputDesc = MutableLiveData<Boolean>()
    val errorInputDesc: LiveData<Boolean>
        get() = _errorInputDesc

    private val _todoItem = MutableLiveData<com.example.simpletodo.domain.TodoItem>()
    val todoItem: LiveData<com.example.simpletodo.domain.TodoItem>
        get() = _todoItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getTodoItem(todoItemId: Int) {
        val item = getTodoItemUseCase.execute(todoItemId)
        _todoItem.value = item
    }

    fun addTodoItem(inputDesc: String?, isHigh: Boolean) {
        val desc = parseDesc(inputDesc)
        val validInput = isValidInput(desc, isHigh)
        if (validInput) {
            val item = com.example.simpletodo.domain.TodoItem(
                desc = desc,
                isHigh = isHigh,
                isComplete = false
            )
            addTodoItemUseCase.execute(item)
            finishWork()
        }
    }

    fun editTodoItem(inputDesc: String?, isHigh: Boolean) {
        val desc = parseDesc(inputDesc)
        val validInput = isValidInput(desc, isHigh)
        if (validInput) {
            _todoItem.value?.let {
                val item = it.copy(desc = desc, isHigh = isHigh)
                editTodoItemUseCase.execute(item)
                finishWork()
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