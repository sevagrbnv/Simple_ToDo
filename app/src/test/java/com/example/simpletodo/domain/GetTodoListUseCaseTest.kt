package com.example.simpletodo.domain

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.MutableLiveData
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.mockito.Mockito

class InstantExecutorExtension : BeforeEachCallback, AfterEachCallback {
    override fun beforeEach(context: ExtensionContext?) {
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) = runnable.run()
            override fun postToMainThread(runnable: Runnable) = runnable.run()
            override fun isMainThread(): Boolean = true
        })
    }

    override fun afterEach(context: ExtensionContext?) {
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
}


@ExtendWith(InstantExecutorExtension::class)
class GetTodoListUseCaseTest {

    private val repository = Mockito.mock(TodoListRepository::class.java)

    @AfterEach
    fun `clean repository`() {
        Mockito.reset(repository)
    }

    @Test
    fun `test GetTodoListUseCase`() {



        val list = mutableListOf<TodoItem>().apply {
            for (i in 0..5) {
                add(
                    TodoItem(
                        desc = "Test $i",
                        isComplete = true,
                        isHigh = false,
                        id = i
                    )
                )
            }
        }

        val ld = MutableLiveData<List<TodoItem>>().apply { value = list }

        Mockito.`when`(repository.getTodoItemList()).thenReturn(ld)

        val result = GetTodoListUseCase(todoListRepository = repository).execute()
        Assertions.assertEquals(ld, result)
    }
}