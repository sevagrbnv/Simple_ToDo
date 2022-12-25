package com.example.simpletodo.domain

class AddTodoItemUseCase(private val todoListRepository: TodoListRepository) {

    fun execute(todoItem: TodoItem) {
        todoListRepository.addTodoItem(todoItem)
    }
}