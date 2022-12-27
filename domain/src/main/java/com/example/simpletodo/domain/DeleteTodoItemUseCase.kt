package com.example.simpletodo.domain

class DeleteTodoItemUseCase(private val todoListRepository: TodoListRepository) {

    suspend fun execute(todoItem: TodoItem) {
        todoListRepository.deleteTodoItem(todoItem)
    }
}