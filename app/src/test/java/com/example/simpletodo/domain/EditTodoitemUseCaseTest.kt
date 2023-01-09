package com.example.simpletodo.domain

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class EditTodoitemUseCaseTest {

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
            Mockito.`when`(repository.editTodoItem(data)).thenReturn(Unit)
            EditTodoItemUseCase(todoListRepository = repository).execute(data)
            EditTodoItemUseCase(todoListRepository = repository).execute(data)
            Mockito.verify(repository, Mockito.times(2)).editTodoItem(data)

        }
    }
}