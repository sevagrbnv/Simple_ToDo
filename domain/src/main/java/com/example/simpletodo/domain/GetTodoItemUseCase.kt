package com.example.simpletodo.domain

class GetTodoItemUseCase(private val todoListRepository: TodoListRepository) {

    suspend fun execute(todoItemId: Int): TodoItem {
        return todoListRepository.getTodoItem(todoItemId)
    }
}