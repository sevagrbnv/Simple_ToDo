package com.example.simpletodo.domain

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class AddTodoItemUseCaseTest {

    @Test
    fun `test AddTodoItemUseCase`() = runBlocking<Unit> {

        val data = TodoItem(
            desc = "Test",
            isComplete = true,
            isHigh = false,
            id = 5
        )

        launch {
            val repository = Mockito.mock(TodoListRepository::class.java)
            Mockito.`when`(repository.addTodoItem(data)).thenReturn(Unit)
            AddTodoItemUseCase(todoListRepository = repository).execute(data)
            AddTodoItemUseCase(todoListRepository = repository).execute(data)
            Mockito.verify(repository, Mockito.times(2)).addTodoItem(data)

        }
    }
}
