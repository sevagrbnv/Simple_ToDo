package com.example.simpletodo.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetTodoListUseCase @Inject constructor(
    private val todoListRepository: TodoListRepository
    ) {

    fun execute(): LiveData<List<TodoItem>> {
        return todoListRepository.getTodoItemList()
    }
}