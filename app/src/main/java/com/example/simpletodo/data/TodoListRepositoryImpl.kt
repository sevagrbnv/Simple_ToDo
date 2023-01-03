package com.example.simpletodo.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.simpletodo.domain.TodoItem
import com.example.simpletodo.domain.TodoListRepository
import javax.inject.Inject

class TodoListRepositoryImpl @Inject constructor(
    private val todoListDao: TodoDao,
    private val mapper: TodoListMapper
) : TodoListRepository {


    override suspend fun addTodoItem(todoItem: TodoItem) {
        todoListDao.addTodo(mapper.mapItemToEntity(todoItem))
    }

    override suspend fun deleteTodoItem(todoItem: TodoItem) {
        todoListDao.deleteTodo(todoItem.id)
    }

    override suspend fun editTodoItem(todoItem: TodoItem) {
        todoListDao.addTodo(mapper.mapItemToEntity(todoItem))
    }

    override suspend fun getTodoItem(todoItemId: Int): TodoItem {
        val dbModel = todoListDao.getTodo(todoItemId)
        return mapper.mapEntityToItem(dbModel)
    }

    override fun getTodoItemList(): LiveData<List<TodoItem>> = Transformations.map(
        todoListDao.getTodoList()
    ) {
        mapper.mapListEntityToListItem(it)
    }
}