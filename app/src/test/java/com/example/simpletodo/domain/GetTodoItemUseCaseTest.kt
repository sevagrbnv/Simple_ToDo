package com.example.simpletodo.domain

import kotlinx.coroutines.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class GetTodoItemUseCaseTest {


    @Test
    fun `test GetTodoItemUseCase`() = runBlocking<Unit> {

        val repository = mock(TodoListRepository::class.java)

        val data = TodoItem(
            desc = "Test",
            isComplete = true,
            isHigh = false,
            id = 5
        )

        launch {
            val index = 5
            Mockito.`when`(repository.getTodoItem(index)).thenReturn(
                TodoItem(
                    desc = "Test",
                    isComplete = true,
                    isHigh = false,
                    id = index
                )
            )
            val result = GetTodoItemUseCase(todoListRepository = repository).execute(index)
            Assertions.assertEquals(data, result)
        }

    }
}