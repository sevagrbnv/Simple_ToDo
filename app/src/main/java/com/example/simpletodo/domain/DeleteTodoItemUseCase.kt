package com.example.simpletodo.domain

import javax.inject.Inject

class DeleteTodoItemUseCase @Inject constructor(
    private val todoListRepository: TodoListRepository
    ) {

    suspend fun execute(todoItem: TodoItem) {
        todoListRepository.deleteTodoItem(todoItem)
    }
}