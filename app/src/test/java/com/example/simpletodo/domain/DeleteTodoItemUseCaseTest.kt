package com.example.simpletodo.domain

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class DeleteTodoItemUseCaseTest {

    @Test
    fun `test DeleteTodoItemUseCase`() = runBlocking<Unit> {

        val data = TodoItem(
            desc = "Test",
            isComplete = true,
            isHigh = false,
            id = 5
        )

        launch {
            val repository = Mockito.mock(TodoListRepository::class.java)
            Mockito.`when`(repository.deleteTodoItem(data)).thenReturn(Unit)
            DeleteTodoItemUseCase(todoListRepository = repository).execute(data)
            DeleteTodoItemUseCase(todoListRepository = repository).execute(data)
            Mockito.verify(repository, Mockito.times(2)).deleteTodoItem(data)

        }
    }
}