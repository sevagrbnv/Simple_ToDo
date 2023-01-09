package com.example.simpletodo.presentation

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.example.simpletodo.domain.*
import com.example.simpletodo.presentation.MainFragment.MainViewModel
import kotlinx.coroutines.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.mockito.Mockito

class InstantExecutorExtension : BeforeEachCallback, AfterEachCallback {
    override fun beforeEach(context: ExtensionContext?) {
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: java.lang.Runnable) = runnable.run()
            override fun postToMainThread(runnable: java.lang.Runnable) = runnable.run()
            override fun isMainThread(): Boolean = true
        })
    }

    override fun afterEach(context: ExtensionContext?) {
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
}


@ExtendWith(InstantExecutorExtension::class)
class MainViewModelTest {

    val getTodoListUseCase = Mockito.mock(GetTodoListUseCase::class.java)
    val deleteTodoItemUseCase = Mockito.mock(DeleteTodoItemUseCase::class.java)
    val editTodoItemUseCase = Mockito.mock(EditTodoItemUseCase::class.java)
    val repository = Mockito.mock(TodoListRepository::class.java)

    lateinit var viewModel: MainViewModel
    lateinit var todoItem: TodoItem

    @BeforeEach
    fun createViewModelAndData() {
        viewModel = MainViewModel(
            getTodoListUseCase,
            deleteTodoItemUseCase,
            editTodoItemUseCase
        )

        todoItem = TodoItem(
            desc = "Test 0",
            isComplete = true,
            isHigh = false,
            id = 0
        )
    }

    @AfterEach
    fun reset() {
        Mockito.reset(getTodoListUseCase)
        Mockito.reset(deleteTodoItemUseCase)
        Mockito.reset(editTodoItemUseCase)
    }

    @Test
    fun `test deleteTodoItem`(){
        CoroutineScope(Dispatchers.Default).launch {
        Mockito.`when`(deleteTodoItemUseCase.execute(todoItem)).thenReturn(Unit)
            viewModel.deleteTodoItem(todoItem)
            Mockito.verify(deleteTodoItemUseCase, Mockito.times(1)).execute(todoItem)
    }}

    @Test
    fun `test changeItemCompleteState`(){
        CoroutineScope(Dispatchers.Default).launch {
            Mockito.`when`(editTodoItemUseCase.execute(todoItem)).thenReturn(Unit)
            viewModel.changeItemCompleteState(todoItem)
            Mockito.verify(editTodoItemUseCase, Mockito.times(1)).execute(todoItem)
            Assertions.assertEquals(todoItem.isComplete, false)
        }
    }
}