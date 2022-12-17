package com.example.simpletodo.domain

class GetTodoListUseCase(private val todoListRepository: TodoListRepository) {

    fun execute(): List<TodoItem> {
        return todoListRepository.getTodoItemList()
    }
}