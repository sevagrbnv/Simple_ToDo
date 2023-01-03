package com.example.simpletodo.domain

import javax.inject.Inject

class AddTodoItemUseCase @Inject constructor(
    private val todoListRepository: TodoListRepository
    ) {

    suspend fun execute(todoItem: TodoItem) {
        todoListRepository.addTodoItem(todoItem)
    }
}