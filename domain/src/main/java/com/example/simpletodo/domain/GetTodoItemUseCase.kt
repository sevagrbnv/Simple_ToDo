package com.example.simpletodo.domain

class GetTodoItemUseCase(private val todoListRepository: TodoListRepository) {

    fun execute(todoItemId: Int): TodoItem {
        return todoListRepository.getTodoItem(todoItemId)
    }
}