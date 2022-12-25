package com.example.simpletodo.domain

class EditTodoItemUseCase(private val todoListRepository: TodoListRepository) {

    fun execute(todoItem: TodoItem) {
        todoListRepository.editTodoItem(todoItem)
    }
}