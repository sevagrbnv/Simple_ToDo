package com.example.simpletodo.domain

class AddTodoItemUseCase(private val todoListRepository: TodoListRepository) {

    suspend fun execute(todoItem: TodoItem) {
        todoListRepository.addTodoItem(todoItem)
    }
}