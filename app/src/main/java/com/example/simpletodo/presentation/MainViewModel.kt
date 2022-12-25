package com.example.simpletodo.presentation
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val repository = com.example.simpletodo.data.TodoListRepositoryImpl

    private val getTodoListUseCase = com.example.simpletodo.domain.GetTodoListUseCase(repository)
    private val deleteTodoListUseCase =
        com.example.simpletodo.domain.DeleteTodoItemUseCase(repository)
    private val editTodoItemUseCase = com.example.simpletodo.domain.EditTodoItemUseCase(repository)

    val todoList = getTodoListUseCase.execute()

    fun deleteTodoItem(todoItem: com.example.simpletodo.domain.TodoItem) {
        deleteTodoListUseCase.execute(todoItem)
    }

    // for change state of checkbox on item
    fun changeItemCompleteState(todoItem: com.example.simpletodo.domain.TodoItem) {
        val newItem = todoItem.copy(isComplete = !todoItem.isComplete)
        editTodoItemUseCase.execute(newItem)
    }

    fun isEmptyList(): Boolean {
        return todoList.value.isNullOrEmpty()
    }
}