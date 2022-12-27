package com.example.simpletodo.domain

import androidx.lifecycle.LiveData

class GetTodoListUseCase(private val todoListRepository: TodoListRepository) {

    fun execute(): LiveData<List<TodoItem>> {
        return todoListRepository.getTodoItemList()
    }
}