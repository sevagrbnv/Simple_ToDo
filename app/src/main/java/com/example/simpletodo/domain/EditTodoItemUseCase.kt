package com.example.simpletodo.domain

import javax.inject.Inject

class EditTodoItemUseCase @Inject constructor(
    private val todoListRepository: TodoListRepository
    ) {

    suspend fun execute(todoItem: TodoItem) {
        todoListRepository.editTodoItem(todoItem)
    }
}