package com.example.simpletodo.domain

import javax.inject.Inject

class GetTodoItemUseCase @Inject constructor(
    private val todoListRepository: TodoListRepository
    ) {

    suspend fun execute(todoItemId: Int): TodoItem {
        return todoListRepository.getTodoItem(todoItemId)
    }
}