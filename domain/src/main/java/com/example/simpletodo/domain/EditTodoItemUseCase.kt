package com.example.simpletodo.domain

class EditTodoItemUseCase(private val todoListRepository: TodoListRepository) {

    suspend fun execute(todoItem: TodoItem) {
        todoListRepository.editTodoItem(todoItem)
    }
}